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

    <!-- 商品详情内容 -->
    <div class="container my-5 flex-grow-1">
      <div class="row">
        <!-- 商品大图 -->
        <div class="col-md-6">
          <img :src="this.product.picUrl" alt="商品图片" class="img-fluid rounded" style="max-height: 400px; object-fit: cover;">
        </div>
        <!-- 商品信息 -->
        <div class="col-md-6">
          <h2>{{ this.product.name }}</h2>
          <h3 class="text-primary">￥{{ product.price}}</h3>
          <p><strong>库存:</strong> {{ product.stockQuantity }} 件</p>
          <div class="input-group mb-3" style="width: 150px;">
            <button class="btn btn-outline-secondary" @click="decreaseQuantity">-</button>
            <input type="text" class="form-control text-center" v-model.number="quantity" readonly>
            <button class="btn btn-outline-secondary" @click="increaseQuantity">+</button>
          </div>
          <button class="btn btn-primary btn-lg" @click="addToCart" :disabled="quantity > product.stock">
            加入购物车
          </button>
          <div v-if="addMessage" class="alert alert-success mt-3" role="alert">
            {{ addMessage }}
          </div>
          <!-- 商品介绍 -->
          <div class="mt-4">
            <h4>商品介绍</h4>
            <p>{{ product.description }}</p>
          </div>
        </div>
      </div>

      <!-- 用户评价 -->
      <div class="mt-5">
        <h4>用户评价</h4>
        <div class="card">
          <div class="card-body">
            <div v-for="review in reviews" :key="review.id" class="border-bottom pb-3 mb-3">
              <div class="d-flex justify-content-between">
                <div>
                  <strong>{{ review.user }}</strong>
                  <div>
                    <span v-for="n in 5" :key="n" class="text-warning">
                      <i class="bi bi-star-fill" v-if="n <= review.rating"></i>
                      <i class="bi bi-star" v-else></i>
                    </span>
                  </div>
                </div>
                <small class="text-muted">{{ review.date }}</small>
              </div>
              <p class="mt-2">{{ review.comment }}</p>
            </div>
            <div v-if="reviews.length === 0" class="text-center text-muted">
              暂无评价
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

export default {
  name: 'ProductDetail',
  data() {
    return {
      product: {
        productId:0,
        name:'',
        description:'',
        price:0,
        stockQuantity:0,
        categoryId:0,
        picUrl:'',
        rating:2
      },
      quantity: 1,
      cartCount: 3, // 模拟购物车数量
      addMessage: '',
      username:'',
      userId:0,
      reviews: [
        { id: 1, user: '用户A', rating: 5, comment: '非常新鲜，口感很好！', date: '2025-06-01' },
        { id: 2, user: '用户B', rating: 4, comment: '味道不错，但包装可以改进。', date: '2025-05-28' }
      ]
    };
  },
  methods: {
    increaseQuantity() {
      if (this.quantity < this.product.stock) {
        this.quantity += 1;
      }
    },
    decreaseQuantity() {
      if (this.quantity > 1) {
        this.quantity -= 1;
      }
    },
    addToCart() {
      if (localStorage.getItem('userId')==null){
        alert("请先登录")
        return
      }
      this.addMessage = `${this.product.name} x${this.quantity} 已加入购物车！`;
      this.cartCount += this.quantity;
      this.quantity = 1;
      setTimeout(() => { this.addMessage = ''; }, 3000);
    },
  },
  mounted() {
    this.username=localStorage.getItem('username')
    this.userId=localStorage.getItem('userId')
    console.log(parseInt(this.$route.query.productId))
    axios({
      method:'GET',
      url:'http://localhost:8080/products/getSingleProducts',
      params:{
        productId:parseInt(this.$route.query.productId)
      }
    }).then(result=>{
      console.log(result.data)
      this.product = result.data
      console.log(this.product)
    })
  }
};
</script>

<style scoped>
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
.bi-star-fill, .bi-star {
  font-size: 1rem;
}
</style>