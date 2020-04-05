// pages/userFriend/userFriend.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userFriend:[],
        friendSum:0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let that = this;
        util.request(api.userFriend).then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    userFriend: res.data.list,
                    friendSum:res.data.list.length
                });
            }
        });
    }


})