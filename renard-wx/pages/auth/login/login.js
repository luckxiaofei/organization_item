var api = require('../../../config/api.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

var app = getApp();
Page({
    data: {
        shareId: 0
    },
    onLoad: function (options) {
        let that = this;
        if (options.shareId) {
            that.setData({
                shareId: options.shareId
            })
        }
    },
    wxLogin: function (e) {
        if (e.detail.userInfo == undefined) {
            app.globalData.hasLogin = false;
            util.showErrorToast('微信登录失败');
            return;
        }
        user.checkLogin().catch(() => {
            user.loginByWeixin(e.detail.userInfo).then(res => {
                app.globalData.hasLogin = true;

                wx.navigateBack({
                    delta: 1
                })
            }).catch((err) => {
                app.globalData.hasLogin = false;
                util.showErrorToast('微信登录失败');
            });
        });
    }
})