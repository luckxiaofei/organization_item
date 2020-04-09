// pages/myInfo/myInfo.js
const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentDate: new Date().getTime(),
    minDate: new Date().getTime(),
    formatter(type, value) {
      if (type === 'year') {
        return `${value}年`;
      } else if (type === 'month') {
        return `${value}月`;
      }
      return value;
    },
    show: false,
    userName: '',
    birthday: ''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let that = this;
    util.request(api.getUserInfo, {}).then(function(res) {
      console.info(util.formatTime2(new Date(res.data.birthday)))
      that.setData({
        userName: res.data.userName,
        birthday: util.formatTime2(new Date(res.data.birthday)),
      })
    });
  },

  confirm(event) {
    console.info(util.formatTime2(new Date(event.detail)))
    this.setData({
      currentDate: event.detail,
      birthday: util.formatTime2(new Date(event.detail)),
      show: false
    });
  },
  showPopup() {
    this.setData({
      show: true
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  onChangeName(event) {
    this.setData({
      userName: event.detail
    })
  },
  updateUserInfo() {
    let that = this;
    if (that.data.userName == null || that.data.userName == '') {
      util.showErrorToast('请填写姓名');
      return;
    }
    if (that.data.birthday == null || that.data.birthday == '') {
      util.showErrorToast('请填写生日');
      return;
    }
    util.request(api.updateUserInfo, {
      userName: that.data.userName,
      birthday: that.data.birthday,
    }).then(function(res) {
      wx.navigateBack({
        delta: 1
      })
    });
  }
})