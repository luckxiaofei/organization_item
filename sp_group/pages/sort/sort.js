// pages/group/group.js
//获取应用实例
const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');


Page({

    /**
     * 页面的初始数据
     */
    data: {
        maskFlag: true,
        subject: '',
        peopleNumber: 1,
        needName: false,
        userInfo: {},
        openId: ""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var that = this;
        //获取用户信息
        wx.getUserInfo({
            success: function (res) {
                console.log(res);
                that.data.userInfo = res.userInfo;
                that.setData({
                    userInfo: that.data.userInfo
                })
            }
        })
    },

    //获取主题值
    useSubjectInput: function (e) {
        this.setData({
            subject: e.detail
        })
    },

    //获取参与人数
    usePnumInput: function (e) {
        this.setData({
            peopleNumber: e.detail
        })
    },

    //获取是否需要填写姓名
    isNeedName: function ({detail}) {
        this.setData({
            needName: detail
        })
    },


    submit: function (e) {
        if (this.data.subject == "") {
            wx.showToast({
                title: '请填写活动主题！',
                icon: 'none'
            })
            return;
        }
        if (parseInt(this.data.gnum) > parseInt(this.data.pnum)) {
            wx.showToast({
                title: '参与人数不能大于总分组数！',
                icon: 'none'
            })
            return;
        }

        var that = this;
        wx.request({
            url: 'http://192.168.51.17:8080/saveGroup', //仅为示例，并非真实的接口地址
            method: "POST",
            data: {
                subject: this.data.subject,
                peopleNumber: this.data.peopleNumber,
                groupNumber: this.data.groupNumber,
                needName: this.data.needName
            },
            header: {
                "Content-Type": "application/json;charsetset=UTF-8"// 默认值
            },
            success: function (res) {
                if (res.data.status == 1) {
                    console.info("======发布id:" + res.data.data.group.id);
                    var openid = "";
                    wx.getStorage({
                        key: 'openid',
                        success(res) {
                            console.log(res.data)
                            that.setData({
                                openId: res.data
                            })
                        }
                    })
                    console.info("--0-00-0-0-0-0-0-0-0======" + that.data.openId)
                    wx.navigateTo({
                        url: '../group/group_detail?id=' + res.data.data.group.id + "&new=true&openId=" + that.data.openId
                    })
                } else {
                    wx.showToast({
                        title: '发布失败，请重试',
                        icon: 'none'
                    })
                }
                console.log(res.data)
            }
        })
    },
    isInteger: function (obj) {
        return Math.floor(obj) === obj
    }

})