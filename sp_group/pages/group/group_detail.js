// pages/group/group_detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    new:false,
    groupDetails:[],
    pnum:'',
    rnum:'',
    subject:'',
    userInfo:{},
    openId:'',
    isCy:false,
    cyInfo:'',
    cyz:'',
 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that  = this ;
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
    this.shareView = this.selectComponent("#shareView")
    this.setData({
      id: options.id,
      new:options.new,
      openId:options.openId
    })
    if (this.data.new) {
      this.shareView.toggleDialog(true);
    }
    if(this.data.id==""){
    
      wx.showToast({
        title: '获取任务信息失败！',
        icon: 'none'
      })
    }else{
      var that = this;
      wx.request({
        url: 'http://192.168.51.17:8080/getGroup', //仅为示例，并非真实的接口地址
        method: "POST",
        data: {
          id: this.data.id, 
          userId:this.data.userInfo.id,
          openId:this.data.openId
        },
        header: {
          "Content-Type": "application/x-www-form-urlencoded;charsetset=UTF-8"// 默认值
        },
        success: function (res) {
          if(res.data!=null){
            that.setData({
              subject:res.data.subject,
              pnum: res.data.pnum,
              rnum: res.data.rnum,
              groupDetails: res.data.groupDetails,
              isCy:res.data.cy,
              cyz:res.data.cyz,
            })
            debugger
            if (res.data.cy){
              that.setData({
               cyInfo:"您被分配到"+res.data.cyz
              })

            }else{
              debugger
              that.setData({
                cyInfo: "您未参与分组，请点击参与分组" 
              })
            }
          }
          if (res.data.status == 1) {
            console.info("======发布id:" + res.data.data.group.id);
            wx.navigateTo({
              url: '../group/group_detail?id=' + res.data.data.group.id
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
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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
  onShareAppMessage: function () {

  },
  
  showShareView: function () {
    this.shareView.toggleDialog(true);
  }
})