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
        subject: '',
        peopleNumber: 1,
        groupNumber: 1,
        needName: false,
        userInfo: {},
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

        let that = this;
        util.request(api.addSortSubject, {
            name: that.data.subject,
            peopleSum: that.data.peopleNumber,
            needName: that.data.needName
        }).then(function (res) {
            util.navigateTo("/pages/sort/sort_detail?subjectId=" + res.data)
        });
    },

})
