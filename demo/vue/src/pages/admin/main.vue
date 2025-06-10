<template>
  <div class="d-flex flex-column min-vh-100">
    <!-- 顶栏 -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">网上超市 - 管理员后台</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" href="#" @click.prevent="logout">退出登录</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- 主内容区域 -->
    <div class="d-flex flex-grow-1">
      <!-- 侧栏 -->
      <div class="bg-light sidebar p-3" style="width: 250px;">
        <h5 class="mb-3">管理菜单</h5>
        <div class="accordion" id="sidebarAccordion">
          <!-- 用户管理 -->
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#userMenu">
                用户管理
              </button>
            </h2>
            <div id="userMenu" class="accordion-collapse collapse">
              <div class="accordion-body">
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('userList')">用户列表</a>
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('userRoles')">角色管理</a>
              </div>
            </div>
          </div>
          <!-- 商品管理 -->
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#productMenu">
                商品管理
              </button>
            </h2>
            <div id="productMenu" class="accordion-collapse collapse">
              <div class="accordion-body">
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('productList')">商品列表</a>
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('productCategories')">分类管理</a>
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('productInventory')">库存管理</a>
              </div>
            </div>
          </div>
          <!-- 管理员个人信息 -->
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#adminMenu">
                个人信息
              </button>
            </h2>
            <div id="adminMenu" class="accordion-collapse collapse">
              <div class="accordion-body">
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('adminProfile')">个人资料</a>
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('adminPassword')">修改密码</a>
              </div>
            </div>
          </div>
          <!-- 订单管理 -->
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#orderMenu">
                订单管理
              </button>
            </h2>
            <div id="orderMenu" class="accordion-collapse collapse">
              <div class="accordion-body">
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('orderList')">订单列表</a>
                <a href="#" class="d-block mb-2 text-decoration-none" @click.prevent="setActiveSection('orderStatus')">状态跟踪</a>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 内容区域 -->
      <div class="flex-grow-1 p-4">
        <h2>{{ activeSectionTitle }}</h2>
        <!-- 用户列表 -->
        <div v-if="activeSection === 'userList'">
          <h3>用户列表</h3>
          <table class="table table-striped">
            <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>邮箱</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" @click="editUser(user.id)">编辑</button>
                <button class="btn btn-sm btn-danger" @click="deleteUser(user.id)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <!-- 商品列表 -->
        <div v-if="activeSection === 'productList'">
          <h3>商品列表</h3>
          <table class="table table-striped">
            <thead>
            <tr>
              <th>ID</th>
              <th>商品名称</th>
              <th>价格</th>
              <th>库存</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="product in products" :key="product.id">
              <td>{{ product.id }}</td>
              <td>{{ product.name }}</td>
              <td>￥{{ product.price.toFixed(2) }}</td>
              <td>{{ product.stock }}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" @click="editProduct(product.id)">编辑</button>
                <button class="btn btn-sm btn-danger" @click="deleteProduct(product.id)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <!-- 个人资料 -->
        <div v-if="activeSection === 'adminProfile'">
          <h3>个人资料</h3>
          <form>
            <div class="mb-3">
              <label for="adminName" class="form-label">用户名</label>
              <input type="text" class="form-control" id="adminName" v-model="adminProfile.name" disabled>
            </div>
            <div class="mb-3">
              <label for="adminEmail" class="form-label">邮箱</label>
              <input type="email" class="form-control" id="adminEmail" v-model="adminProfile.email">
            </div>
            <button type="button" class="btn btn-primary" @click="saveProfile">保存</button>
          </form>
        </div>
        <!-- 其他功能占位 -->
        <div v-if="activeSection === 'userRoles'">
          <h3>角色管理</h3>
          <p>此处为角色管理功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'productCategories'">
          <h3>分类管理</h3>
          <p>此处为分类管理功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'productInventory'">
          <h3>库存管理</h3>
          <p>此处为库存管理功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'adminPassword'">
          <h3>修改密码</h3>
          <p>此处为修改密码功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'orderList'">
          <h3>订单列表</h3>
          <p>此处为订单列表功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'orderStatus'">
          <h3>状态跟踪</h3>
          <p>此处为订单状态跟踪功能（待实现）。</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as bootstrap from 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js';

export default {
  name: 'AdminDashboard',
  data() {
    return {
      activeSection: 'userList',
      users: [
        { id: 1, username: 'user1', email: 'user1@example.com' },
        { id: 2, username: 'user2', email: 'user2@example.com' },
      ],
      products: [
        { id: 1, name: '新鲜苹果', price: 5.99, stock: 100 },
        { id: 2, name: '有机牛奶', price: 12.99, stock: 50 },
      ],
      adminProfile: {
        name: 'admin',
        email: 'admin@example.com'
      }
    };
  },
  computed: {
    activeSectionTitle() {
      const titles = {
        userList: '用户列表',
        userRoles: '角色管理',
        productList: '商品列表',
        productCategories: '分类管理',
        productInventory: '库存管理',
        adminProfile: '个人资料',
        adminPassword: '修改密码',
        orderList: '订单列表',
        orderStatus: '状态跟踪'
      };
      return titles[this.activeSection] || '管理面板';
    }
  },
  methods: {
    setActiveSection(section) {
      this.activeSection = section;
    },
    editUser(id) {
      alert(`编辑用户 ID: ${id}`);
    },
    deleteUser(id) {
      this.users = this.users.filter(user => user.id !== id);
      alert(`已删除用户 ID: ${id}`);
    },
    editProduct(id) {
      alert(`编辑商品 ID: ${id}`);
    },
    deleteProduct(id) {
      this.products = this.products.filter(product => product.id !== id);
      alert(`已删除商品 ID: ${id}`);
    },
    saveProfile() {
      alert('个人资料已保存！');
    },
    logout() {
      alert('已退出登录！');
      // 实际应用中可跳转到登录页面
    }
  }
};
</script>

<style scoped>
.sidebar {
  border-right: 1px solid #dee2e6;
}
.accordion-button {
  font-weight: 500;
}
.accordion-button:not(.collapsed) {
  background-color: #e9ecef;
}
a.text-decoration-none:hover {
  color: #007bff;
}
</style>