<template>
    <div>
    <!--头部-->
    <top></top>
    <!--导航-->
    <navs></navs>
    <!--核心内容start-->
    <section class="contact-us">

<div class="container">
  <div class="row">
    <div class="col-lg-12 align-self-center">
      <div class="row">
        <div class="col-lg-12">




          <el-form id="contact"  ref="dataForm" :rules="rules" :model="temp">
            <div class="row">
              <div class="col-lg-12">
                <h2>留言联系我们</h2>
              </div>
              <div class="col-lg-4">
                <fieldset>
                  <el-form-item prop="username">
                    <input v-model="temp.username"   type="text"  placeholder="姓名..." required="">
                  </el-form-item>
                </fieldset>
              </div>
              <div class="col-lg-4">
                <fieldset>
                  <el-form-item prop="tel">
                    <input v-model="temp.tel"  type="text"  placeholder="电话" required="">
                  </el-form-item>
                </fieldset>
              </div>

              <div class="col-lg-4">
                <fieldset>
                  <el-form-item prop="email">
                    <input v-model="temp.email" type="text" pattern="[^ @]*@[^ @]*" placeholder="邮箱..." required="">
                  </el-form-item>
                </fieldset>
              </div>


              <div class="col-lg-12" prop="content">
                <fieldset>
                  <textarea v-model="temp.content" type="text" class="form-control" id="message" placeholder="申请内容..." required=""></textarea>
                </fieldset>
              </div>



              <div class="col-lg-12">
                <fieldset>
                  <button type="submit" id="form-submit" class="button" @click="addSave()">现在发送消息</button>
                </fieldset>
              </div>

            </div>
          </el-form>









        </div>
      </div>
    </div>
   <!-- <div class="col-lg-3">
      <div class="right-info">
        <ul>
          <li>
            <h6>手机号码</h6>
            <span>010-020-0340</span>
          </li>
          <li>
            <h6>邮箱地址</h6>
            <span>info@meeting.edu</span>
          </li>
          <li>
            <h6>街道地址</h6>
            <span>Rio de Janeiro - RJ, 22795-008, Brazil</span>
          </li>
          <li>
            <h6>网站 URL</h6>
            <span>www.meeting.edu</span>
          </li>
        </ul>
      </div>
    </div>-->
  </div>
</div>

</section>

<foot></foot>
  

    <!--核心内容end-->

   
    </div>

</template>

<script>
    import top from '../../qdutils/top/index'
    import navs from '../../qdutils/navs/index'
    import foot from '../../qdutils/footer/index'
    import service from '@/utils/request'
    export default {
      name: "index",
      components:{top,navs,foot},
      data() {

return {
  acitity: {},
  team: {},
  temp:{
    username:'',
    tel:'',
    email:'',
    content:''
  },
  rules: {
    username: [{ required: true, message: '请填写正确的姓名格式(3个长度以上)', trigger: 'blur' }],
    tel:[{ required: true, message: '请填写电话', trigger: 'blur' },{
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: "请输入正确的手机号码",
      trigger: "blur"
    }],
    email:[{ required: true, message: '请填写邮箱', trigger: 'blur' },{
      pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
      message: "请输入正确的邮箱",
      trigger: "blur"
    }],
    content: [{ required: true, message: '请填写留言内容', trigger: 'blur' }]

  }

}
},

methods: {

//添加
addSave(temp) {
  //加验证规则
  this.$refs['dataForm'].validate((valid) => {
    if (valid) {

      //向后端发送请求，实现添加功能
      service.post('leaveinfo/addInfo', this.temp).then(res => {
        if (res.code == 20000) {
          //关闭弹出框
          this.temp.content='';
          this.$refs.dataForm.resetFields();//清空所有的表单信息
          this.$alert("留言成功...");

        } else {
          this.$alert("留言失败...");
        }
      })

    }
  })

}

}

    }
</script>

<style scoped>
    @import "../../../vendor/bootstrap/css/bootstrap.min.css";
    @import "../../../assets/css/templatemo-edu-meeting.css";
</style>