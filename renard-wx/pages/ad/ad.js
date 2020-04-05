// pages/ad/ad.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    adId: 1212
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  adLoad: function() {
    console.log('小程序视频广告加载成功')
  },
  adError: function(err) {
    console.log('小程序视频广告加载失败', err)
  },
  adClose: function() {
    console.log('小程序视频广告关闭')
  }



})