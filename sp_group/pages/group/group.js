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
        groupNumber: 1,
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
    //获取分组组数
    useGnumInput: function (e) {
        this.setData({
            groupNumber: e.detail
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

        let that = this;
        util.request(api.addGroupSubject, {
            name: this.data.subject,
            peopleSum: this.data.peopleNumber,
            groupSum: this.data.groupNumber,
            needName: this.data.needName
        }).then(function (res) {
            util.navigateTo("/pages/group/group_detail?subjectId="+res.data)
        });
    },

})