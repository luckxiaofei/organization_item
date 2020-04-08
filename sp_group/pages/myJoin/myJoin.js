// pages/myCaeate/myCaeate.js
const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';

Page({

    /**
     * 页面的初始数据
     */
    data: {
        subjectList: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let that = this;
        util.request(api.getMyCreateSubject, {}).then(function (res) {
            that.setData({
                subjectList: res.data
            })
        });
    },
})
