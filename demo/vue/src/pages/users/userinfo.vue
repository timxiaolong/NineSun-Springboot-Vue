<template>
  <div class="d-flex flex-column min-vh-100">
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
              <a class="nav-link" href="#">首页</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">分类</a>
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

    <!-- 个人中心内容 -->
    <div class="container my-5 flex-grow-1">
      <h2 class="mb-4 text-center">个人中心</h2>
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card p-4 shadow">
            <!-- 头像 -->
            <div class="text-center mb-4">
              <img :src="user.avatar" alt="用户头像" class="rounded-circle" style="width: 120px; height: 120px; object-fit: cover;">
              <div class="mt-2">
                <button class="btn btn-outline-primary btn-sm" @click="changeAvatar">更改头像</button>
              </div>
            </div>

            <!-- 选项卡导航 -->
            <ul class="nav nav-tabs mb-4">
              <li class="nav-item">
                <button class="nav-link" :class="{ active: activeTab === 'profile' }" @click="activeTab = 'profile'">个人信息</button>
              </li>
              <li class="nav-item">
                <button class="nav-link" :class="{ active: activeTab === 'orders' }" @click="activeTab = 'orders'">订单查询</button>
              </li>
              <li class="nav-item">
                <button class="nav-link" :class="{ active: activeTab === 'password' }" @click="activeTab = 'password'">修改密码</button>
              </li>
            </ul>

            <!-- 个人信息 -->
            <div v-if="activeTab === 'profile'">
              <form @submit.prevent="saveProfile">
                <div class="mb-3">
                  <label for="username" class="form-label">用户名</label>
                  <input type="text" class="form-control" id="username" v-model="user.username" required>
                </div>
                <div class="mb-3">
                  <label for="email" class="form-label">邮箱</label>
                  <input type="email" class="form-control" id="email" v-model="user.email" required>
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label">电话</label>
                  <input type="tel" class="form-control" id="phone" v-model="user.phone">
                </div>
                <div class="text-center">
                  <button type="submit" class="btn btn-primary">保存</button>
                </div>
              </form>
            </div>

            <!-- 订单查询 -->
            <div v-if="activeTab === 'orders'">
              <div v-if="orders.length === 0" class="alert alert-info text-center">
                暂无订单
              </div>
              <table v-else class="table table-striped">
                <thead>
                <tr>
                  <th>订单编号</th>
                  <th>日期</th>
                  <th>总价</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="order in orders" :key="order.id">
                  <td>{{ order.id }}</td>
                  <td>{{ order.date }}</td>
                  <td>￥{{ order.total.toFixed(2) }}</td>
                  <td>{{ order.status }}</td>
                  <td>
                    <button class="btn btn-sm btn-primary" @click="viewOrderDetails(order.id)">查看详情</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>

            <!-- 修改密码 -->
            <div v-if="activeTab === 'password'">
              <form @submit.prevent="changePassword">
                <div class="mb-3">
                  <label for="currentPassword" class="form-label">当前密码</label>
                  <input type="password" class="form-control" id="currentPassword" v-model="passwordForm.currentPassword" required>
                </div>
                <div class="mb-3">
                  <label for="newPassword" class="form-label">新密码</label>
                  <input type="password" class="form-control" id="newPassword" v-model="passwordForm.newPassword" required>
                </div>
                <div class="mb-3">
                  <label for="confirmNewPassword" class="form-label">确认新密码</label>
                  <input type="password" class="form-control" id="confirmNewPassword" v-model="passwordForm.confirmNewPassword" required>
                </div>
                <div class="text-center">
                  <button type="submit" class="btn btn-primary">修改密码</button>
                </div>
              </form>
            </div>

            <!-- 消息提示 -->
            <div v-if="message" class="alert mt-3" :class="messageType" role="alert">
              {{ message }}
            </div>
          </div>
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
  name: 'UserProfile',
  data() {
    return {
      activeTab: 'profile',
      user: {
        avatar: 'https://via.placeholder.com/150?text=用户头像',
        username: 'user123',
        email: 'user123@example.com',
        phone: '138-1234-5678'
      },
      passwordForm: {
        currentPassword: '',
        newPassword: '',
        confirmNewPassword: ''
      },
      orders: [
        { id: 'ORD001', date: '2025-06-01', total: 45.97, status: '已完成' },
        { id: 'ORD002', date: '2025-05-28', total: 12.99, status: '待发货' }
      ],
      cartCount: 3, // 模拟购物车数量
      message: '',
      messageType: ''
    };
  },
  methods: {
    changeAvatar() {
      this.user.avatar = 'https://via.placeholder.com/150?text=新头像';
      this.showMessage('头像已更新！', 'alert-success');
    },
    saveProfile() {
      if (!this.user.username || !this.isValidEmail(this.user.email)) {
        this.showMessage('请填写有效的用户名和邮箱！', 'alert-danger');
        return;
      }
      if (this.user.phone && !this.isValidPhone(this.user.phone)) {
        this.showMessage('请输入有效的手机号码！', 'alert-danger');
        return;
      }
      this.showMessage('个人信息已保存！', 'alert-success');
    },
    changePassword() {
      if (!this.passwordForm.currentPassword || !this.passwordForm.newPassword || !this.passwordForm.confirmNewPassword) {
        this.showMessage('请填写所有密码字段！', 'alert-danger');
        return;
      }
      if (this.passwordForm.newPassword !== this.passwordForm.confirmNewPassword) {
        this.showMessage('新密码和确认密码不一致！', 'alert-danger');
        return;
      }
      this.showMessage('密码已修改！', 'alert-success');
      this.passwordForm = { currentPassword: '', newPassword: '', confirmNewPassword: '' };
    },
    viewOrderDetails(orderId) {
      alert(`查看订单 ${orderId} 详情`);
      // 实际应用中可跳转到订单详情页面
    },
    goToCart() {
      alert('跳转到购物车页面');
      // 实际应用中可使用路由跳转
    },
    showMessage(text, type) {
      this.message = text;
      this.messageType = type;
      setTimeout(() => { this.message = ''; }, 3000);
    },
    isValidEmail(email) {
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    },
    isValidPhone(phone) {
      return /^1[3-9]\d{9}$/.test(phone);
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
.nav-tabs .nav-link {
  color: #495057;
}
.nav-tabs .nav-link.active {
  color: #007bff;
}
</style>