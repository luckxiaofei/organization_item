var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    activeNames: ["2"],
    notice_info: '',
    scoreSum: 0,
    shareScoreSum: 0,
    todayScoreSum: 0,
    todayShareScoreSum: 0,
    scoreTop: [],
    rule_info: '',
    friendImage: '',
    friendSum: 0,
    showShareImage: false,
    frieshareImageUrlndSum: 0,
  },
  onLoad(event) {
    this.getBaseInfo();
  },
  getBaseInfo() {
    let that = this;
    util.request(api.getScoreBaseInfo).then(function(res) {
      if (res.errno === 0) {
        that.setData({
          notice_info: res.data.notice_info,
          scoreSum: res.data.scoreSum,
          shareScoreSum: res.data.shareScoreSum,
          todayScoreSum: res.data.todayScoreSum,
          todayShareScoreSum: res.data.todayShareScoreSum,
          scoreTop: res.data.scoreTop,
          rule_info: res.data.rule_info,
          friendSum: res.data.friendSum,
        });
      }
    });
  },
  onChange(event) {
    this.setData({
      activeNames: event.detail
    });
  },
  clickSignIn() {
    let that = this;
    util.request(api.updateScore, {
      //3浏览商品，4广告，5邀请好友，6签到
      type: 6
    }).then(function(res) {
      if (res.errno === 0) {
        if (res.data == 0) {
          wx.showToast({
            title: "已签到，请勿重复签到"
          })
          return;
        }
        wx.showToast({
          title: '签到成功'
        })
      }
      that.getBaseInfo();
    });
  },
  clickAd() {
    wx.navigateTo({
      url: "/pages/ad/ad"
    })
  },
  clickBrowseGoods() {
    wx.switchTab({
      url: "/pages/index/index"
    })
  },

  goScoreDetail() {
    wx.navigateTo({
      url: "/pages/userScoreHistory/userScoreHistory"
    })
  },
  goShareMoneyDetail() {
    wx.navigateTo({
      url: "/pages/userMoney/userMoney?onlyCommission" + true
    })
  },
  clickShareFriend() {
    let that = this;
    util.request(api.createFriendShareImage, {
      scene: 7,
    }).then(function(res) {
      that.setData({
        showShareImage: true,
        friendImage: res.data
      })
    });
  },
  onClickHide() {
    this.setData({
      showShareImage: false
    });
  }

})