<template>
  <div class="d-flex flex-column min-vh-100">
    <!-- 顶栏（与首页一致） -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
      <div class="container">
        <a class="navbar-brand" href="/">网上超市</a>
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

    <!-- 注册内容 -->
    <div class="container d-flex justify-content-center align-items-center flex-grow-1">
      <div class="card p-4 shadow" style="max-width: 400px; width: 100%;">
        <h2 class="text-center mb-4">注册</h2>
        <form @submit.prevent="handleRegister">
          <div class="mb-3">
            <label for="username" class="form-label">用户名</label>
            <input
                type="text"
                class="form-control"
                id="username"
                v-model="form.username"
                placeholder="请输入用户名"
                required
            >
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密码</label>
            <input
                type="password"
                class="form-control"
                id="password"
                v-model="form.password"
                placeholder="请输入密码"
                required
            >
          </div>
          <div class="mb-3">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <input
                type="password"
                class="form-control"
                id="confirmPassword"
                v-model="form.confirmPassword"
                placeholder="请再次输入密码"
                required
            >
          </div>
          <div class="mb-3">
            <label for="email" class="form-label">邮箱</label>
            <input
                type="email"
                class="form-control"
                id="email"
                v-model="form.email"
                placeholder="请输入邮箱"
                required
            >
          </div>
          <div class="mb-3">
            <label for="phone" class="form-label">手机号码</label>
            <input
                type="tel"
                class="form-control"
                id="phone"
                v-model="form.phone"
                placeholder="请输入手机号码"
            >
          </div>
          <button type="submit" class="btn btn-primary w-100">注册</button>
        </form>
        <div v-if="errorMessage" class="alert alert-danger mt-3" role="alert">
          {{ errorMessage }}
        </div>
        <div v-if="successMessage" class="alert alert-success mt-3" role="alert">
          {{ successMessage }}
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
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    return {
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        phone: ''
      },
      cartCount: 0, // 模拟购物车数量
      errorMessage: '',
      successMessage: ''
    };
  },
  methods: {
    handleRegister() {
      // 重置消息
      this.errorMessage = '';
      this.successMessage = '';

      // 验证输入
      if (!this.form.username || !this.form.password || !this.form.confirmPassword || !this.form.email) {
        this.errorMessage = '请填写所有必填字段！';
        return;
      }

      if (this.form.password !== this.form.confirmPassword) {
        this.errorMessage = '两次输入的密码不一致！';
        return;
      }

      if (!this.isValidEmail(this.form.email)) {
        this.errorMessage = '请输入有效的邮箱地址！';
        return;
      }

      if (this.form.phone && !this.isValidPhone(this.form.phone)) {
        this.errorMessage = '请输入有效的手机号码！';
        return;
      }

      // 模拟注册成功
      this.successMessage = '注册成功！请登录。';
      this.form = { username: '', password: '', confirmPassword: '', email: '', phone: '' };
      setTimeout(() => {
        this.successMessage = '';
        // 实际应用中可跳转到登录页面
        alert('跳转到登录页面');
      }, 2000);
    },
    isValidEmail(email) {
      // 简单邮箱格式验证
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    },
    isValidPhone(phone) {
      // 简单手机号码验证（中国大陆11位）
      return /^1[3-9]\d{9}$/.test(phone);
    },
    goToCart() {
      alert('跳转到购物车页面');
      // 实际应用中可使用路由跳转
    }
  }
};
</script>

<style scoped>
.card {
  border-radius: 10px;
}
.btn-primary {
  background-color: #007bff;
  border: none;
}
.btn-primary:hover {
  background-color: #0056b3;
}
.cart-badge {
  position: absolute;
  top: -10px;
  right: -10px;
}
footer {
  background-color: #f8f9fa;
  padding: 2rem 0;
  margin-top: 2rem;
}
</style>