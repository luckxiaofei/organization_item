import Notify from '../../components/notify/index';

var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');

Page({
  data: {
    id: 0,
    goods: {},
    attribute: [],
    issueList: [],
    brand: {},
    specificationList: [],
    productList: [],
    relatedGoods: [],
    cartGoodsCount: 0,
    userHasCollect: 0,
    number: 1,
    checkedSpecText: '规格数量选择',
    tmpSpecText: '请选择规格数量',
    openAttr: false, //是否显示 规格选择窗口
    shareImage: '',
    isConvert: false, //标识是否是一个积分兑换
    soldout: false,
    canWrite: false,
    radio: -1, //规格表单的下标值
    specId: null, //选中的规格id
    shareUserId: '', //分享的用户
    userId: '', //当前用户
    browseGoodsNumber: 2,
    browseGoodsTime: 20, //秒


  },

  // 页面分享
  onShareAppMessage: function() {
    let that = this;
    return {
      title: that.data.goods.name,
      desc: that.data.goods.brief,
      path: '/pages/index/index?goodId=' + that.data.id + "&sharUserId=" + that.data.userId,
    }
  },
  handleSetting:

    function(e) {
      var that = this;
      if (!e.detail.authSetting['scope.writePhotosAlbum']) {
        wx.showModal({
          title: '警告',
          content: '不授权无法保存',
          showCancel: false
        })
        that.setData({
          canWrite: false
        })
      } else {
        wx.showToast({
          title: '保存成功'
        })
        that.setData({
          canWrite: true
        })
      }
    }

    ,
  showShare: function() {
    this.sharePop.togglePopup();
  },


  // 获取商品信息
  getGoodsInfo: function() {
    wx.showLoading({
      title: '加载中',
    });

    let that = this;
    util.request(api.GoodsDetail, {
      id: that.data.id
    }).then(function(res) {
      if (res.errno === 0) {

        let _specificationList = res.data.specificationList
        // 如果仅仅存在一种货品，那么商品页面初始化时默认checked
        if (_specificationList.length == 1) {
          that.setData({
            radio: 0,
            specId: _specificationList[0].id,
          });
        }

        that.setData({
          goods: res.data.info,
          isConvert: res.data.info.isConvert,
          attribute: res.data.attribute,
          issueList: res.data.issue,
          brand: res.data.brand,
          specificationList: res.data.specificationList,
          productList: res.data.productList,
          userHasCollect: res.data.userHasCollect,
          shareImage: res.data.shareImage,
          checkedSpecPrice: res.data.info.retailPrice,
          userId: res.data.userId,
          browseGoodsNumber: res.data.browseGoodsNumber,
          browseGoodsTime: res.data.browseGoodsTime,
        });

        WxParse.wxParse('goodsDetail', 'html', res.data.info.detail, that);
        //获取推荐商品
        that.getGoodsRelated();
        wx.hideLoading();
      }
    });
    wx.hideLoading();
  },

  // 获取推荐商品
  getGoodsRelated: function() {
    let that = this;
    util.request(api.GoodsRelated, {
      id: that.data.id
    }).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          relatedGoods: res.data.list,
        });
      }
    });
  },


  soldoutNotify: function() {
    Notify("商品已售完");
  },

  getCheckedSpecKey: function() {
    let checkedValue = this.getCheckedSpecValue().map(function(v) {
      return v.valueText;
    });
    return checkedValue;
  },

  // 规格改变时
  changeSpecInfo: function(event) {
    //获取选中的规格信息
    const {
      name
    } = event.currentTarget.dataset;
    const {
      spec
    } = event.currentTarget.dataset;
    const {
      specId
    } = event.currentTarget.dataset;
    this.setData({
      radio: name,
      specId: specId
    });

    //判断库存
    let exit = this.getCheckedProductItem();
    if (!exit) {
      this.setData({
        soldout: true
      });
      this.soldoutNotify();
      console.error('规格所对应货品不存在');
      return;
    } else {
      this.setData({
        soldout: false
      });
      this.notify.hide();
    }
  },

  // 获取选中的产品（根据规格）
  getCheckedProductItem: function() {
    let that = this;
    let productList = that.data.productList;
    for (let i = 0; i < productList.length; i++) {
      if (productList[i].specificationid.toString() == that.data.specId.toString()) {
        if (productList[i].number > 0) {
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  },

  onLoad: function(options) {
    // 页面初始化 options为页面跳转所带来的参数
    if (options.id) {
      this.setData({
        id: parseInt(options.id)
      });
    }
    if (options.shareUserId) {
      this.setData({
        shareUserId: parseInt(options.shareUserId)
      });
    }
    let that = this;
    util.request(api.systemInfo).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          browseGoodsNumber: res.data.browse_goods_number,
          browseGoodsTime: res.data.browse_goods_time
        });
      }
    });
    this.getGoodsInfo();
    // wx.getSetting({
    //     success: function (res) {
    //         console.log(res)
    //         //不存在相册授权
    //         if (!res.authSetting['scope.writePhotosAlbum']) {
    //             wx.authorize({
    //                 scope: 'scope.writePhotosAlbum',
    //                 success: function () {
    //                     that.setData({
    //                         canWrite: true
    //                     })
    //                 },
    //                 fail: function (err) {
    //                     that.setData({
    //                         canWrite: false
    //                     })
    //                 }
    //             })
    //         } else {
    //             that.setData({
    //                 canWrite: true
    //             });
    //         }
    //     }
    // })

    setTimeout(function() {
      let browseGoodsNumber = wx.getStorageSync("browseGoodsNumber");
      browseGoodsNumber = browseGoodsNumber == null ? 0 : parseInt(browseGoodsNumber);
      if (parseInt(browseGoodsNumber) + 1 < that.data.browseGoodsNumber) {
        wx.setStorageSync("browseGoodsNumber", parseInt(browseGoodsNumber) + 1)
      } else {
        util.request(api.updateScore, {
          //3浏览商品，4广告，5邀请好友，6签到
          type: 3
        }).then(function(res) {
          if (res.errno === 0) {
            wx.showToast({
              title: "浏览任务完成"
            })
          }
        });
      }
    }, that.data.browseGoodsTime * 1000);

  },

  onShow: function() {

    // 页面显示
    var that = this;

    util.request(api.CartGoodsCount).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          cartGoodsCount: res.data
        });
      }
    });
    if (that.data.shareUserId != null && that.data.shareUserId != '') {
      util.request(api.addFriend, {
        shareUserId: shareUserId
      }).then(function(res) {

      });
    }
  },

  //决定是否打开 规格选择窗口
  autoChangeOpenAttr: function() {
    if (this.data.specId != null && this.data.number > 0) {
      this.setData({
        openAttr: false
      });
    } else {
      this.setData({
        openAttr: true
      });
    }
  },

  //立即购买（先自动加入购物车）
  addFast: function() {
    var that = this;
    that.autoChangeOpenAttr();
    if (that.data.openAttr) {
      return false;
    }
    // wx.showToast({
    //     image: '/static/images/icon_error.png',
    //     title: '请选择完整规格'
    // });


    //根据选中的规格，判断是否有对应的sku信息
    let exit = this.getCheckedProductItem();
    if (!exit) {
      wx.showToast({
        image: '/static/images/icon_error.png',
        title: '没有库存'
      });
      return false;
    }


    //立即购买
    util.request(api.CartFastAdd, {
        goodsId: this.data.goods.id,
        number: this.data.number,
        specificationId: this.data.specId
      }, "POST")
      .then(function(res) {
        if (res.errno == 0) {
          // 如果storage中设置了cartId，则是立即购买，否则是购物车购买
          try {
            wx.setStorageSync('cartId', res.data);
            wx.navigateTo({
              url: '/pages/shopping/checkout/checkout'
            })
          } catch (e) {}

        } else {
          wx.showToast({
            image: '/static/images/icon_error.png',
            title: res.errmsg,
            mask: true
          });
        }
      });


  },

  //添加到购物车
  addToCart: function() {
    var that = this;
    var that = this;
    that.autoChangeOpenAttr();
    if (that.data.openAttr) {
      return false;
    }
    //判断库存
    let exit = this.getCheckedProductItem();
    if (!exit) {
      wx.showToast({
        image: '/static/images/icon_error.png',
        title: '没有库存'
      });
      return false;
    }

    //添加到购物车
    util.request(api.CartAdd, {
        goodsId: this.data.goods.id,
        number: this.data.number,
        specificationId: this.data.specId
      }, "POST")
      .then(function(res) {
        let _res = res;
        if (_res.errno == 0) {
          wx.showToast({
            title: '添加成功'
          });
          that.setData({
            openAttr: !that.data.openAttr,
            cartGoodsCount: _res.data
          });

        } else {
          wx.showToast({
            image: '/static/images/icon_error.png',
            title: _res.errmsg,
            mask: true
          });
        }

      });
  },

  cutNumber: function() {
    this.setData({
      number: (this.data.number - 1 > 1) ? this.data.number - 1 : 1
    });
  },
  addNumber: function() {
    this.setData({
      number: this.data.number + 1
    });
  },
  switchAttrPop: function() {
    if (this.data.openAttr == false) {
      this.setData({
        openAttr: !this.data.openAttr
      });
    }
  },

  closeAttr: function() {
    this.setData({
      openAttr: false,
    });
  },
  openCartPage: function() {
    wx.switchTab({
      url: '/pages/cart/cart'
    });
  },
  onReady: function() {
    // 页面渲染完成
    this.sharePop = this.selectComponent("#sharePop");
    this.notify = this.selectComponent("#van-notify");
  },


})