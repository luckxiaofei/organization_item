// pages/group/group.js
//获取应用实例
const app = getApp()




Page({

  /**
   * 页面的初始数据
   */
  data: {
    maskFlag: true,
    subject: '',
    pnum:'',
    gnum:'',
    switch1Checked:false,
    userInfo:{},
    openId:""
  },

  //获取主题值
  useSubjectInput: function (e) {
    this.setData({
      subject: e.detail.value
    })
  },
  //获取参与人数
  usePnumInput: function (e) {
    this.setData({
      pnum: e.detail.value
    })
  },
  //获取分组组数
  useGnumInput: function (e) {
    this.setData({
      gnum: e.detail.value
    })
  },
  //获取是否需要填写姓名
  useIsName: function (e) {
    this.setData({
      switch1Checked: e.detail.value
    })
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
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
    console.info("===============页面初始化方法===============");
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  submit: function(e) {
    if (this.data.subject==""){
      wx.showToast({
        title: '请填写活动主题！',
        icon: 'none'
      })
      return;
    }
    if (this.data.pnum==""){
      wx.showToast({
        title: '请填写正确的参与人数！1~99之间',
        icon: 'none'
      })
      return;
    }
    if (!this.isInteger(this.data.pnum) && this.data.pnum<=0){
      wx.showToast({
        title: '请填写正确的参与人数！1~99之间',
        icon: 'none'
      })
      return;
    }
    if (this.data.gnum == "") {
      wx.showToast({
        title: '请填写正确的分组数！1~99之间',
        icon: 'none'
      })
      return;
    }
    if (!this.isInteger(this.data.gnum) && this.data.gnum <= 0) {
      wx.showToast({
        title: '请填写正确的分组数！1~99之间',
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
  
   
    var that = this;
    wx.request({
      url: 'http://192.168.51.17:8080/saveGroup', //仅为示例，并非真实的接口地址
      method: "POST",
      data: {
        subject: this.data.subject,
        pnum:this.data.pnum,
        gnum:this.data.gnum,
        isName:this.data.switch1Checked
      },
      header: {
        "Content-Type": "application/json;charsetset=UTF-8"// 默认值
      },
      success: function(res) {
        if(res.data.status==1){
          console.info("======发布id:"+res.data.data.group.id);
          var openid = "";
          wx.getStorage({
            key: 'openid',
            success(res) {
              console.log(res.data)
              that.setData({
                openId: res.data
              })
            }
          })
          console.info("--0-00-0-0-0-0-0-0-0======" + that.data.openId)
          wx.navigateTo({
            url: '../group/group_detail?id=' + res.data.data.group.id + "&new=true&openId=" + that.data.openId
          })
        }else{
          wx.showToast({
            title: '发布失败，请重试',
            icon:'none'
          })
        }
        console.log(res.data)
      }
    })
  },
  isInteger:function (obj) {
    return Math.floor(obj) === obj
  }

})