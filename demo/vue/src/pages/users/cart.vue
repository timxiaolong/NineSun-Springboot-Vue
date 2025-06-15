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
            <div v-if="this.userId != null" >
              <span style="margin-right: 5px">欢迎用户：{{this.username}}</span>
              <a href="/userinfo" class="btn btn-outline-info me-2">个人中心</a>
            </div>
            <div v-if="this.userId == null">
              <a href="/login" class="btn btn-outline-success me-2">登录</a>
              <a href="/signup" class="btn btn-outline-success me-2">注册</a>
            </div>
          </div>
        </div>
      </div>
    </nav>

    <!-- 购物车内容 -->
    <div class="container my-5 flex-grow-1">
      <h2 class="mb-4 text-center">购物车</h2>
      <div v-if="cart.length === 0" class="alert alert-info text-center">
        购物车为空
      </div>
      <div v-else>
        <!-- 商品列表 -->
        <div class="card mb-4">
          <div class="card-body">
            <table class="table table-hover">
              <thead>
              <tr>
                <th>商品</th>
                <th>名称</th>
                <th>单价</th>
                <th>数量</th>
                <th>小计</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="item in cart" :key="item.id">
                <td>
                  <img :src="item.picUrl" alt="商品图片" style="width: 50px; height: 50px; object-fit: cover;">
                </td>
                <td>{{ item.name }}</td>
                <td>￥{{ item.price }}</td>
                <td>
                  <div class="input-group input-group-sm" style="width: 120px;">
                    <button class="btn btn-outline-secondary" @click="decreaseQuantity(item.id)">-</button>
                    <input type="text" class="form-control text-center" :value="item.quantity" readonly>
                    <button class="btn btn-outline-secondary" @click="increaseQuantity(item.id)">+</button>
                  </div>
                </td>
                <td>￥{{ (item.price * item.quantity) }}</td>
                <td>
                  <button class="btn btn-sm btn-danger" @click="removeFromCart(item.cartItemId)">移除</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 总价和结算 -->
        <div class="d-flex justify-content-end align-items-center">
          <h4 class="me-3">总计: ￥{{ cartTotal }}</h4>
          <button class="btn btn-primary" @click="checkout">结算</button>
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

export default {
  name: 'Cart',
  data() {
    return {
      cart: [
       ],
      username:'',
      userId:''
    };
  },
  computed: {
    cartTotal() {
      return this.cart.reduce((total, item) => total + item.price * item.quantity, 0);
    }
  },
  methods: {
    increaseQuantity(id) {
      const item = this.cart.find(item => item.id === id);
      if (item) {
        item.quantity += 1;
      }
    },
    decreaseQuantity(id) {
      const item = this.cart.find(item => item.id === id);
      if (item && item.quantity > 1) {
        item.quantity -= 1;
      }
    },
    removeFromCart(id) {
      axios({
        method:'DELETE',
        url:'http://localhost:8080/cart-items/deleteCart',
        params:{
          Id:parseInt(id)
        }
      }).then(result=>{
        alert(result.data.message)
        if (result.data.status === 200){
          location.reload()
        }
      })
    },
    checkout() {
      // axios({
      //   url:'http://localhost:8080/cart-items/checkout',
      //   method:'POST',
      //   data:this.cart,
      //   param:{
      //     userId:localStorage.getItem('userId')
      //   }
      // }).then(result=>{
      //   alert(result.data.message)
      //   setTimeout('location.reload()',3000)
      // })
      axios.post('http://localhost:8080/cart-items/checkout',JSON.stringify(this.cart),{
        headers:{ 'Content-Type': 'application/json; charset=UTF-8' },
        params:{userId:localStorage.getItem('userId')}
      }).then(result=>{

      })
    }
  },
  mounted() {
    this.username=localStorage.getItem('username')
    this.userId=localStorage.getItem('userId')
    axios({
      method:'GET',
      url:'http://localhost:8080/cart-items/getcart',
      params:{
        userId:parseInt(localStorage.getItem('userId'))
      }
    }).then(result=>{
      this.cart=result.data
      console.log(this.cart)
    })
  }
};
</script>

<style scoped>
.table img {
  border-radius: 5px;
}
.btn-primary {
  background-color: #007bff;
  border: none;
}
.btn-primary:hover {
  background-color: #0056b3;
}
.input-group .form-control {
  max-width: 50px;
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