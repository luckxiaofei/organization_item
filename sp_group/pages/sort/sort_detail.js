// pages/group/group_detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    id:"123",
    subject:"香港一日游",
    "sortLists": [
      { name: "红鲤鱼与绿鲤鱼与驴", date:"01-09 16:50",sort:"第一名"},
      { name: "红鲤鱼与绿鲤鱼与驴", date: "01-09 16:50", sort: "第二名" },
      { name: "红鲤鱼与绿鲤鱼与驴", date: "01-09 16:50", sort: "第三名" },
    ]
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
    console.log(options)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.shareView = this.selectComponent("#shareView")
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    this.shareView.toggleDialog(false);
    return{
      title: this.data.subject,
      path:"/pages/sort/sort_detail?id="+this.data.id,
      imageUrl:"../images/shareImage.png", 
    }
  },
  showShareView:function(){
    this.shareView.toggleDialog(true);
  }
})