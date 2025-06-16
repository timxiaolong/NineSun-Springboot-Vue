
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
              <th>昵称</th>
              <th>地址</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(user,index) in users" :key="user.id">
              <td>{{ user.userId }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.fullName }}</td>
              <td>{{ user.address }}</td>
              <td>
                <button type="button" class="btn btn-sm btn-primary me-2" data-bs-toggle="modal" data-bs-target="#userProfileModal" @click="setCurrentUserId(index)">
                  编辑
                </button>
                <button class="btn btn-sm btn-danger" @click="deleteUser(user.userId)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <!-- 商品列表 -->
        <div v-if="activeSection === 'productList'">
          <h3>商品列表</h3>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#productProfileModal">新增商品</button>
          <table class="table table-striped">
            <thead>
            <tr>
              <th>ID</th>
              <th>商品名称</th>
              <th>描述</th>
              <th>价格</th>
              <th>库存</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="product in products" :key="product.id">
              <td>{{ product.productId }}</td>
              <td>{{ product.name }}</td>
              <td>{{ product.description }}</td>
              <td>￥{{ product.price }}</td>
              <td>{{ product.stockQuantity }}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" data-bs-toggle="modal" data-bs-target="#productProfileModal" @click="editProduct(product.productId)">编辑</button>
                <button class="btn btn-sm btn-danger" @click="deleteProduct(product.productId)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>

        <div v-if="activeSection === 'productCategories'">
          <h3>种类列表</h3>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#productProfileModal">新增商品</button>
          <table class="table table-striped">
            <thead>
            <tr>
              <th>ID</th>
              <th>种类</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="category in categories" :key="category.id">
              <td>{{ category.categoryId }}</td>
              <td>{{ category.name }}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" data-bs-toggle="modal" data-bs-target="#categoryProfileModal" @click="editProduct(category.categoryId)">编辑</button>
                <button class="btn btn-sm btn-danger" @click="deleteCategory(category.categoryId)">删除</button>
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
        <div v-if="activeSection === 'adminPassword'">
          <h3>修改密码</h3>
          <p>此处为修改密码功能（待实现）。</p>
        </div>
        <div v-if="activeSection === 'orderList'">
          <h3>订单列表</h3>
          <table class="table table-striped">
            <thead>
            <tr>
              <th>订单ID</th>
              <th>用户ID</th>
              <th>总价</th>
              <th>状态</th>
              <th>下单时间</th>
              <th>邮寄地址</th>
              <th>支付方式</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="order in orders" :key="order.id">
              <td>{{ order.orderId }}</td>
              <td>{{ order.userId }}</td>
              <td>{{ order.totalAmount }}</td>
              <td>{{ order.status }}</td>
              <td>{{ order.createdAt }}</td>
              <td>{{ order.shippingAddress }}</td>
              <td>{{ order.paymentMethod }}</td>
              <td>
                <button class="btn btn-sm btn-primary me-2" data-bs-toggle="modal" data-bs-target="#orderProfileModal" @click="editProduct(category.categoryId)">编辑</button>
                <button class="btn btn-sm btn-danger" @click="deleteOrder(order.orderId)">删除</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div v-if="activeSection === 'orderStatus'">
          <h3>状态跟踪</h3>
          <p>此处为订单状态跟踪功能（待实现）。</p>
        </div>
      </div>
    </div>
  </div>

  <!--模态框组件-->
  <div class="modal fade" id="userProfileModal" tabindex="-1" aria-labelledby="userProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="userProfileModalLabel">修改个人信息</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="关闭"></button>
        </div>
        <div class="modal-body">
          <form id="userProfileForm">
            <div class="mb-3">
              <label for="username" class="form-label">用户名</label>
              <input type="text" class="form-control" id="username" v-model="this.changeUserInfo.username" placeholder="请输入用户名" required>
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">邮箱</label>
              <input type="email" class="form-control" id="email" v-model="this.changeUserInfo.email" placeholder="请输入邮箱" required>
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">手机号码</label>
              <input type="tel" class="form-control" id="phone" v-model="this.changeUserInfo.phone" placeholder="请输入手机号码">
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">昵称</label>
              <input type="tel" class="form-control" id="phone" v-model="this.changeUserInfo.fullName" placeholder="请输入昵称">
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">收货地址</label>
              <input type="tel" class="form-control" id="phone" v-model="this.changeUserInfo.address" placeholder="请输入收货地址">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="saveProfile()">保存</button>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="productProfileModal" tabindex="-1" aria-labelledby="productProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="productProfileModalLabel">修改商品信息</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="关闭"></button>
        </div>
        <div class="modal-body">
          <form id="productProfileForm">
            <div class="mb-3">
              <label for="username" class="form-label">商品名</label>
              <input type="text" class="form-control" id="username" v-model="this.singleProduct.name" placeholder="请输入商品名" required>
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">商品介绍</label>
              <input type="email" class="form-control" id="email" v-model="this.singleProduct.description" placeholder="请输入商品介绍" required>
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">价格</label>
              <input type="tel" class="form-control" id="phone" v-model="this.singleProduct.price" placeholder="请输入价格">
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">库存数量</label>
              <input type="tel" class="form-control" id="phone" v-model="this.singleProduct.stockQuantity" placeholder="请输入库存数量">
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">种类</label>
              <input type="tel" class="form-control" id="phone" v-model="this.singleProduct.categoryId" placeholder="请输入种类：1.休闲零食 2.家清日化 3.米面粮油">
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">图片URL</label>
              <input type="tel" class="form-control" id="phone" v-model="this.singleProduct.picUrl" placeholder="请输入图片URL">
            </div>
            <a href="http://106.54.241.217:8080/">在这里上传图片</a>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
          <button type="button" class="btn btn-primary" @click="addOrSaveProduct()">保存</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

import axios from "axios";

export default {
  name: 'AdminDashboard',
  data() {
    return {
      activeSection: 'userList',
      users: [],
      tempUserId:0,
      changeUserInfo:{
        userId:0,
        username:'',
        password:'',
        email:'',
        fullName:'',
        phone:'',
        address:''
      },
      changeProductInfo:{},
      products: [],
      categories:[],
      orders:[],
      singleProduct:{},
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
    setCurrentUserId(userId){
      this.tempUserId = userId;
      this.changeUserInfo=this.users[userId];
      console.log(this.changeUserInfo)
    },
    getUserList(){
      axios({
        method:'GET',
        url:'http://localhost:8080/admins/getAllUsers'
      }).then(result=>{
        this.users=result.data
      })
    },
    getProduct(){
      axios({
        method:'GET',
        url:'http://localhost:8080/products/getproducts',
      }).then(result=>{
        this.products = result.data
        console.log(this.products)
      })
    },
    getCategories(){
      axios({
        method:'get',
        url:'http://localhost:8080/categories/getAllCategories',
      }).then(result=>{
        this.categories=result.data
      })
    },
    getOrders(){
      axios({
        method:'GET',
        url:'http://localhost:8080/orders/getAllOrders'
      }).then(result=>{
        this.orders = result.data
      })
    },
    setActiveSection(section) {
      console.log(section)
      switch (section) {
        case 'userList':{
          this.getUserList()
          break
        }
        case 'userRoles':{
          break
        }
        case 'productList':{
          this.getProduct()
          break
        }
        case 'productCategories':{
          this.getCategories()
          break
        }
        case 'productInventory':{
          break
        }
        case 'adminProfile':{
          break
        }
        case 'adminPassword':{
          break
        }
        case 'orderList':{
          break
        }
        case 'orderStatus':{
          break
        }
        default:{
          alert('没有此功能')
        }
      }
      this.activeSection = section;
    },
    editUser(id) {
      alert(`编辑用户 ID: ${id}`);
    },
    deleteUser(id) {
      axios({
        method:'delete',
        url:'http://localhost:8080/admins/deleteUserById',
        params:{
          userId:id
        }
      }).then(result=>{
        if(result.data.status===200){
          alert(result.data.message)
          setTimeout('location.reload()',3000)
        }else {
          alert(result.data.message)
        }
      })
    },
    addOrSaveProduct(){
      axios({
        method:'POST',
        url:'http://localhost:8080/products/addOrSaveProducts',
        data:this.singleProducts
      }).then(result=>{
        if (result.data.status===200){
          alert(result.data.message)
          location.reload()
        }else {
          alert(result.data.message)
        }
      })
    },
    editProduct(id) {
      axios({
        method:'GET',
        url:'http://localhost:8080/products/getSingleProducts',
        params:{
          productId:parseInt(id)
        }
      }).then(result=>{
        this.singleProduct=result.data
      })
    },
    deleteProduct(id) {
      axios({
        method:'DELETE',
        url:'http://localhost:8080/products/delectProduct',
        params:{
          productId:id
        }
      }).then(result=>{
        if (result.data.status===200){
          alert(result.data.message)
          location.reload()
        }else {
          alert(result.data.message)
        }
      })
    },
    saveProfile() {
      console.log(this.changeUserInfo)
      axios({
        method:'POST',
        url:'http://localhost:8080/admins/changeUserInfo',
        data:this.changeUserInfo
      }).then(result=>{
        if (result.data.status===200){
          alert(result.data.message)
          location.reload()
        }else {
          alert(result.data.message)
        }
      })
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