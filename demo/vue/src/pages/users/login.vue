<template>
  <!-- 顶栏（与首页一致） -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
    <div class="container">
      <a class="navbar-brand" href="#">网上超市</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <a class="nav-link" href="/">首页</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/types">分类</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">促销</a>
          </li>
        </ul>
        <div class="d-flex align-items-center">
          <a href="#login" class="btn btn-outline-success me-2">登录</a>
          <div class="position-relative">
            <button class="btn btn-outline-primary" @click="goToCart">
              购物车
              <span class="badge bg-danger cart-badge" v-if="cartCount">{{ cartCount }}</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </nav>
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
  <!-- 底部（与首页一致） -->
  <footer class="text-center">
    <div class="container">
      <div class="row">
        <div class="col-md-4 mb-3">
          <h5>关于我们</h5>
          <p>网上超市致力于为您提供新鲜、优质的商品和便捷的购物体验。</p>
        </div>
        <div class="col-md-4 mb-3">
          <h5>联系方式</h5>
          <p>邮箱: support@onlineshop.com<br>电话: 400-123-4567</p>
        </div>
        <div class="col-md-4 mb-3">
          <h5>关注我们</h5>
          <p>
            <a href="#" class="text-dark me-2">微博</a>
            <a href="#" class="text-dark">微信</a>
          </p>
        </div>
      </div>
      <p class="mt-3">© 2025 网上超市 版权所有</p>
    </div>
  </footer>
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
            localStorage.setItem('userId',result.data.param.userId)
            console.log(sessionStorage.getItem('userId'))
            alert(result.data.message)
            // setTimeout("location.href=\"/\"",3000)
          }else {
            alert(result.data.message)
          }
        })
      } else {
        axios({
          url:'http://localhost:8080/admins/login',
          method:'GET',
          params:{
            username:this.username,
            password:this.password
          }
        }).then(result=>{
          console.log(result)
          if (result.data.status === 200){
            console.log(result.data)
            window.sessionStorage.setItem('userId',result.data.userId)
            window.sessionStorage.setItem('userName',result.data.username)
            alert(result.data.message)
            setTimeout("location.href=\"/admin\"",3000)
          }else {
            alert(result.data.message)
          }
        })
        // alert(`管理员 ${this.username} 登录成功！`);
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
  background-image: url('');
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