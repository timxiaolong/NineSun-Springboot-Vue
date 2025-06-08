<template>
  <div class="container d-flex justify-content-center align-items-center min-vh-100 background">
    <div class="card p-4 shadow" style="max-width: 400px; width: 100%;">
      <h2 class="text-center mb-4">登录</h2>
      <div class="mb-3">
        <label for="username" class="form-label">用户名</label>
        <input
            type="text"
            class="form-control"
            name="username"
            id="username"
            v-model="username"
            placeholder="请输入用户名"
            required
        >
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">密码</label>
        <input
            type="password"
            class="form-control"
            name="password"
            id="password"
            v-model="password"
            placeholder="请输入密码"
            required
        >
      </div>
      <div class="mb-3">
        <label class="form-label">用户类型</label>
        <div class="form-check">
          <input
              class="form-check-input"
              type="radio"
              name="userType"
              id="user"
              value="user"
              v-model="userType"
              checked
          >
          <label class="form-check-label" for="user">用户</label>
        </div>
        <div class="form-check">
          <input
              class="form-check-input"
              type="radio"
              name="userType"
              id="admin"
              value="admin"
              v-model="userType"
          >
          <label class="form-check-label" for="admin">管理员</label>
        </div>
      </div>
      <button class="btn btn-primary w-100" @click="handleLogin">登录</button>
      <div v-if="Message" class="alert alert-danger mt-3" role="alert">
        {{ Message }}
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: 'Login',
  data() {
    return {
      userType:'user',
      Message:''
    };
  },
  methods: {
    handleLogin() {
      console.log(this.userType)
      if (!this.username || !this.password) {
        this.Message = '用户名和密码不能为空！';
        return;
      }
      // 模拟登录逻辑
      if (this.userType === 'user') {
        axios({
          url:'http://localhost:8080/users/login',
          method:'GET',
          params:{
            username:this.username,
            password:this.password
          }
        }).then(result=>{
          console.log(result)
          if (result.data.status === 200){
            localStorage.setItem('username',result.data.param.username)
            localStorage.setItem('adminId',result.data.param.id)
            alert(result.data.message)
            setTimeout("location.href=\"/\"",3000)
          }else {
            alert(result.data.message)
          }
        })
      } else {
        alert(`管理员 ${this.username} 登录成功！`);
      }
      this.errorMessage = '';
      this.username = '';
      this.password = '';
    }
  }
};
</script>

<style scoped>
.background {
  background-image: url('https://img.alicdn.com/imgextra/i3/O1CN01Sf6dER1zbJ3uVQ0lE_!!6000000006732-0-tps-846-472.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}
.card {
  border-radius: 10px;
  background-color: rgba(255, 255, 255, 0.9); /* 半透明背景以确保卡片内容可读 */
}
.btn-primary {
  background-color: #007bff;
  border: none;
}
.btn-primary:hover {
  background-color: #0056b3;
}
</style>