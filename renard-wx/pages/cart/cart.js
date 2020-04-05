var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');

var app = getApp();

Page({
    data: {
        cartGoods: [],
        cartTotal: {
            "goodsCount": 0,
            "goodsAmount": 0.00,
            "checkedGoodsCount": 0,
            "checkedGoodsAmount": 0.00
        },
        checkedAllStatus: true,
        hasLogin: false
    },
    onShow: function () {
        this.getCartList()
    },
    goLogin() {
        wx.navigateTo({
            url: "/pages/auth/login/login"
        });
    },

    onPullDownRefresh() {
        wx.showNavigationBarLoading() //在标题栏中显示加载
        this.getCartList();
        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
    },

    getCartList: function () {
        let that = this;
        util.request(api.CartList).then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    cartGoods: res.data.cartList,
                    cartTotal: res.data.cartTotal
                });

                that.setData({
                    checkedAllStatus: that.isCheckedAll()
                });
            }
        });
    },
    isCheckedAll: function () {
        //判断购物车商品已全选
        return this.data.cartGoods.every(function (element, index, array) {
            if (element.checked == true) {
                return true;
            } else {
                return false;
            }
        });
    },
    doCheckedAll: function () {
        let checkedAll = this.isCheckedAll()
        this.setData({
            checkedAllStatus: this.isCheckedAll()
        });
    },

    getCheckedGoodsCount: function () {
        let checkedGoodsCount = 0;
        this.data.cartGoods.forEach(function (v) {
            if (v.checked === true) {
                checkedGoodsCount += v.number;
            }
        });
        console.log(checkedGoodsCount);
        return checkedGoodsCount;
    },
    checkedItem: function (event) {
        let itemIndex = event.target.dataset.itemIndex;
        let that = this;
        var goodsIdArray = [];
        goodsIdArray.push(that.data.cartGoods[itemIndex].goodsId);
        let checked = !that.data.cartGoods[itemIndex].checked;
        that.commonCheckRequest(goodsIdArray, checked);
    },
    checkedAll: function () {
        let that = this;
        var goodsIdArray = [];
        var isChecked = false;
        let cartGoods = this.data.cartGoods;
        for (let i = 0; i < cartGoods.length; i++) {
            let goodsId = cartGoods[i].goodsId;
            goodsIdArray.push(goodsId)
            if (!cartGoods[i].checked) {
                isChecked = true;
            }
        }
        that.commonCheckRequest(goodsIdArray, isChecked)
    },

    commonCheckRequest(goodsIdArray, isChecked) {
        let that = this;
        util.request(api.CartChecked, {
            goodsIdArray: goodsIdArray,
            isChecked: isChecked,
        }, 'POST').then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    cartGoods: res.data.cartList,
                    cartTotal: res.data.cartTotal
                });
                that.setData({
                    checkedAllStatus: that.isCheckedAll()
                });
            }
        });

    },
    deleteCart: function (event) {
        //获取已选择的商品
        let that = this;
        if (event.detail != 'right') {
            return false;
        }
        let goodsId = event.currentTarget.dataset.goodsId;
        let goodsIdList = [];
        goodsIdList.push(goodsId);
        util.request(api.CartDelete, {
            goodsIdList: goodsIdList
        }, 'POST').then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    cartGoods: res.data.cartList,
                    cartTotal: res.data.cartTotal
                });
                wx.showToast({title: "删除成功"})
            }

            that.setData({
                checkedAllStatus: that.isCheckedAll()
            });
        });
    },
    updateCart: function (event) {
        let that = this;

        let goodsId = event.currentTarget.dataset.goodsId;
        let id = event.currentTarget.dataset.id;
        let productId = event.currentTarget.dataset.productId;
        let number = event.detail;

        util.request(api.CartUpdate, {
            productId: productId,
            goodsId: goodsId,
            number: number,
            id: id
        }, 'POST').then(function (res) {
            that.setData({
                cartGoods: res.data.cartList,
                cartTotal: res.data.cartTotal
            });
            that.setData({
                checkedAllStatus: that.isCheckedAll()
            });
        });

    },
    checkoutOrder: function () {
        //获取已选择的商品
        let that = this;

        var checkedGoods = this.data.cartGoods.filter(function (element, index, array) {
            if (element.checked == true) {
                return true;
            } else {
                return false;
            }
        });

        if (checkedGoods.length <= 0) {
            wx.showToast({
                image: '/static/images/icon_error.png',
                title: '请选中需要下单的商品'
            });
            return false;
        }

        // storage中设置了cartId，则是购物车购买
        try {
            wx.setStorageSync('cartId', 0);
            wx.navigateTo({
                url: '../shopping/checkout/checkout'
            })
        } catch (e) {
        }

    },

})