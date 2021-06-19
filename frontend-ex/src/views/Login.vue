<template>
  <el-container>
    <el-header style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);height: 80px">
      <h1>OurCOIN</h1>
      <h1>知识图谱</h1>
    </el-header>
    <el-main class="main">
      <el-tabs class="tab-pane" type="border-card" :stretch=true>
        <el-tab-pane label="登陆">
          <el-form :model="loginRuleForm" :rules="loginRules" ref="loginRuleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item  style="width:60%;margin-left: 14%;margin-top: 3%" prop="email">
              <div>
                <el-input v-model="loginRuleForm.email" placeholder="邮箱">
                  <template #prefix>
                    <i class="iconfont" style="margin-left: 3%">&#xe621;</i>
                  </template>
                </el-input>
              </div>
            </el-form-item>
            <el-form-item  style="width:60%;margin-left: 14%;margin-top: 3%" prop="password">
              <div>
                <el-input type="password" v-model="loginRuleForm.password" placeholder="密码">
                  <template #prefix>
                    <i class="iconfont" style="margin-left: 5%">&#xe609;</i>
                  </template>
                </el-input>
              </div>
            </el-form-item>
            <el-form-item style="width:60%;margin-left: 14%;margin-top: 3%">
              <el-button type="primary" @click="handleLogin" style="width: 50%">登陆</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="注册">
          <el-form :model="registerRuleForm" :rules="registerRules" ref="registerRuleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item  style="width:60%;margin-left: 14%;margin-top: 3%" prop="email">
              <div>
                <el-input v-model="registerRuleForm.email" placeholder="邮箱">
                  <template #prefix>
                    <i class="iconfont" style="margin-left: 3%">&#xe621;</i>
                  </template>
                </el-input>
              </div>
            </el-form-item>
            <el-form-item  style="width:60%;margin-left: 14%;margin-top: 3%" prop="password">
              <div>
                <el-input type="password" v-model="registerRuleForm.password" placeholder="密码">
                  <template #prefix>
                    <i class="iconfont" style="margin-left: 5%">&#xe609;</i>
                  </template>
                </el-input>
              </div>
            </el-form-item>
            <el-form-item  style="width:60%;margin-left: 14%;margin-top: 3%" prop="confirmPassword">
              <div>
                <el-input type="password" v-model="registerRuleForm.confirmPassword" placeholder="确认密码">
                  <template #prefix>
                    <i class="iconfont" style="margin-left: 5%">&#xe609;</i>
                  </template>
                </el-input>
              </div>
            </el-form-item>
            <el-form-item style="width:60%;margin-left: 14%;margin-top: 3%">
              <el-button type="primary" @click="handleRegister" style="width: 48%;float: left">注册</el-button>
              <el-button type="info" @click="resetRegister('registerRuleForm')" style="width: 48%">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>

</template>

<script>

  export default {
    name: 'Login',
    components: {

    },
    computed: {
      // ...mapGetters([
      //   'token'
      // ])
    },
    mounted() {

    },
    watch: {
      // $route: {
      //   handler: function(route) {
      //     this.redirect = route.query && route.query.redirect
      //   },
      //   immediate: true
      // },
    },
    data() {
      return {
        loginRuleForm: {
          email: '',
          password: '',
        },
        loginRules: {
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { validator: this.checkEmail , validateTrigger: 'blur'}
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
        },
        registerRuleForm: {
          email: '',
          password: '',
          confirmPassword: '',
        },
        registerRules: {
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { validator: this.checkEmail , validateTrigger: 'blur'}
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { validator: this.checkPassword , validateTrigger: 'blur'}
          ],
          confirmPassword: [
            { required: true, message: '请再次输入密码', trigger: 'blur' },
            { validator: this.checkConfirmPassword , validateTrigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      checkEmail(rule, value, callback) {
        const re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
        if (re.test(value)) {
          callback();
        } else {
          callback(new Error('请输入有效邮箱'));
        }
        callback()
      },
      checkPassword(rule, value, callback){
        if (value.length < 6) {
          callback(new Error('密码长度至少6位'))
        }
        callback()
      },
      checkConfirmPassword(rule, value, callback){
        if (value && this.registerRuleForm.password && value.trim() !== this.registerRuleForm.password.trim()) {
          callback(new Error('两次密码不一致'))
        }
        callback()
      },
      handleLogin(){
        console.log(this.loginRuleForm)
      },
      handleRegister(){
        console.log(this.registerRuleForm)
      },
      resetRegister(formName){
        this.$refs[formName].resetFields();
      },
    }
  }
</script>


<style>
  @font-face {
    font-family: 'iconfont';  /* project id 2264197 */
    src: url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.eot');
    src: url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.eot?#iefix') format('embedded-opentype'),
    url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.woff2') format('woff2'),
    url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.woff') format('woff'),
    url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.ttf') format('truetype'),
    url('//at.alicdn.com/t/font_2264197_4jb2syjsejl.svg#iconfont') format('svg');
  }
  .iconfont{
    font-family:"iconfont" !important;
    font-size:20px;font-style:normal;
    -webkit-font-smoothing: antialiased;
    -webkit-text-stroke-width: 0.2px;
    -moz-osx-font-smoothing: grayscale;
    color: gray;
  }
  .main{
    height: 500px;

  }
  .tab-pane{
    margin-top: 3%;
    margin-left: 25%;
    height: 70%;
    width: 50%;
  }
</style>

