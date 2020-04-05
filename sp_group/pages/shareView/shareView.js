// pages/shareView/shareView.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    showDialog: false,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toggleDialog: function() {
      this.setData({
        showDialog: !this.data.showDialog
      })
    }
  }
})