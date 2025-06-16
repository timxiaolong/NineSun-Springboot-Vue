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
          </ul>
          <div class="d-flex align-items-center">
            <div v-if="this.userId != null" >
              <span style="margin-right: 5px">欢迎用户：{{this.username}}</span>
              <a href="/userinfo" class="btn btn-outline-info me-2">个人中心</a>
              <a class="btn btn-outline-primary" href="/cart">
                购物车
              </a>
            </div>
            <div v-if="this.userId == null">
              <a href="/login" class="btn btn-outline-success me-2">登录</a>
              <a href="/signup" class="btn btn-outline-success me-2">注册</a>
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
              <img src="http://106.54.241.217:8080/images/2025/06/13/PixPin_2025-06-14_10-13-44.md.png" alt="用户头像" class="rounded-circle" style="width: 120px; height: 120px; object-fit: cover;">
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
                <div class="mb-3">
                  <label for="address" class="form-label">地址</label>
                  <input type="tel" class="form-control" id="address" v-model="user.address">
                </div>
                <div class="text-center">
                  <button type="submit" class="btn btn-primary">保存</button>
                </div>
                <div class="text-center">
                  <button class="btn btn-primary" style="margin-top: 30px" @click="exit()">退出登录</button>
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
                  <th>收货地址</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="order in orders" :key="order.id">
                  <td>{{ order.orderId }}</td>
                  <td>{{ order.shippingAddress }}</td>
                  <td>{{ order.status }}</td>
                  <td>
                    <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#orderModal" @click="getOrderItems(order.orderId)">查看详情</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>

            <!--订单信息模态框-->
            <div class="modal fade" id="orderModal" tabindex="-1" aria-labelledby="orderModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="orderModalLabel">订单详情</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <!-- 订单基本信息 -->
                    <div class="mb-4">
                      <h6 class="fw-bold">订单信息</h6>
                      <div class="row">
                        <div class="col-md-6">
                          <p class="mb-2"><strong>订单ID：</strong> {{ orderTitle.orderId }}</p>
                          <p class="mb-2"><strong>下单时间：</strong> {{ orderTitle.orderTime }}</p>
                        </div>
                        <div class="col-md-6">
                          <p class="mb-2"><strong>状态：</strong> <span class="badge bg-success">{{ orderTitle.status }}</span></p>
                          <p class="mb-2"><strong>收货地址：</strong> {{ orderTitle.shippingAddress }}</p>
                        </div>
                      </div>
                    </div>

                    <!-- 商品列表 -->
                    <div>
                      <h6 class="fw-bold mb-3">商品列表</h6>
                      <div class="table-responsive">
                        <div class="table table-hover">
                          <div class="row bg-light py-2">
                            <div class="col-3 fw-bold">商品名称</div>
                            <div class="col-3 fw-bold">数量</div>
                            <div class="col-3 fw-bold">单价</div>
                            <div class="col-3 fw-bold">小计</div>
                          </div>
                          <div v-for="item in order_items" :key="item.orderItemsId" class="row py-2 border-bottom">
                            <div class="col-3">{{ item.name }}</div>
                            <div class="col-3">{{ item.quantity }}</div>
                            <div class="col-3">¥{{ item.price }}</div>
                            <div class="col-3">¥{{ (item.quantity * item.price) }}</div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="modal-footer d-flex justify-content-between align-items-center">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">关闭</button>
                  </div>
                </div>
              </div>
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
import axios from "axios";
import {compile} from "vue";

export default {
  name: 'UserProfile',
  data() {
    return {
      activeTab: 'profile',
      user: {
        userId:0,
        username: '',
        email: '',
        phone: '',
        fullName:'',
        createAt:'',
        lastLogin:'',
        address:''
      },
      username:'',
      userId:'',
      passwordForm: {
        currentPassword: '',
        newPassword: '',
        confirmNewPassword: ''
      },
      orders: [],
      orderTitle:{},
      order_items:[],
      message: '',
      messageType: ''
    };
  },
  methods: {
    getOrderItems(orderId){
      console.log(this.orders)
      this.orderTitle = this.orders.find(order => order.orderId === orderId)
      console.log(orderId)
      console.log(this.orderTitle)
      axios({
        method:'GET',
        url:'http://localhost:8080/order-items/getOrderItems',
        params:{
          orderId:orderId
        }
      }).then(result=>{
        console.log(result.data)
        this.order_items = result.data
        console.log(this.order_items)
      })
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
      console.log(this.user)
      axios({
        method:'POST',
        url:'http://localhost:8080/users/changeUserInfo',
        data:this.user,
        headers:{
          'Content-Type':'application/json'
        }
      }).then(result=>{
        if (result.data.status === 200){
          alert(result.data.message)
          location.reload()
        }else {
          alert(result.data.message)
        }
      })
    },
    exit(){
      localStorage.clear()
      alert('退出登录，正在跳转主页')
      location.href="/"
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
      axios({
        method:'POST',
        url:'http://localhost:8080/users/changeUserPassword',
        params:{
          newPassword:this.passwordForm.newPassword,
          oldPassword:this.passwordForm.currentPassword,
          userId:parseInt(localStorage.getItem('userId'))
        }
      }).then(result=>{
        if (result.data.status === 200){
          alert(result.data.message)
          localStorage.clear()
          setTimeout('this.$route.push(\'/\')',3000)
        }else {
          this.showMessage(result.data.message,'alert-danger')
        }
      })
      this.passwordForm = { currentPassword: '', newPassword: '', confirmNewPassword: '' };
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
  },
  mounted() {
    this.username=localStorage.getItem('username')
    this.userId=localStorage.getItem('userId')
    //获取用户信息
    axios({
      method:'get',
      url:'http://localhost:8080/users/getUserInfo',
      params:{
        id:parseInt(this.userId)
      }
    }).then(result=>{
      this.user = result.data
      console.log(result.data)
      console.log(this.user)
    })
    //获取订单信息
    axios({
      method:'GET',
      url:'http://localhost:8080/orders/getUserOrders',
      params:{
        userId:parseInt(localStorage.getItem('userId'))
      }
    }).then(result=>{
      this.orders = result.data
    })
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