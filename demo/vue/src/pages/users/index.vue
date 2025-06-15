<template>
  <div class="container-fluid">
    <!-- 导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
      <div class="container">
        <a class="navbar-brand" href="#">网上超市</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link active" href="#">首页</a>
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

    <!-- 轮播图 -->
    <div class="container mb-4">
      <div id="carouselExample" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="http://106.54.241.217:8080/images/2025/06/10/VCG211376854868.jpg" class="d-block w-100" alt="促销1">
          </div>
          <div class="carousel-item">
            <img src="http://106.54.241.217:8080/images/2025/06/10/VCG211354915574.jpg" class="d-block w-100" alt="促销2">
          </div>
          <div class="carousel-item">
            <img src="http://106.54.241.217:8080/images/2025/06/10/VCG211467334623.jpg" class="d-block w-100" alt="促销3">
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">上一页</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">下一页</span>
        </button>
      </div>
    </div>

    <!-- 产品展示 -->
    <div class="container">
      <h2 class="mb-4">热门商品</h2>
      <div class="row row-cols-1 row-cols-md-4 g-4">
        <div v-for="product in products" :key="product.id" class="col">
          <div class="card product-card h-100">
            <img :src="product.image" class="card-img-top" alt="商品图片">
            <div class="card-body">
              <h5 class="card-title">{{ product.name }}</h5>
              <p class="card-text">￥{{ product.price }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部 -->
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
            <a href="/faq" class="text-dark">有问题？点我！</a>
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
  name: 'App',
  data() {
    return {
      products: [
        { id: 1, name: '加厚垃圾袋', price: 5.99, image: 'http://106.54.241.217:8080/images/2025/06/10/efae3841782a3d4ff86f109b85a9f280.png' },
        { id: 2, name: '大魔王素毛肚', price: 12.99, image: 'http://106.54.241.217:8080/images/2025/06/10/0b92a58f20517d177e3abeae6f5aaf11.png' },
        { id: 3, name: '全麦面包', price: 8.50, image: 'https://via.placeholder.com/150' },
        { id: 4, name: '优质牛肉', price: 29.99, image: 'https://via.placeholder.com/150' },
      ],
      username:'',
      userId:'',
      cart: [

      ]
    };
  },
  computed: {
    cartTotal() {
      return this.cart.reduce((total, item) => total + item.price, 0);
    }
  },
  methods: {
    addToCart(product) {
      this.cart.push(product);
      alert(`${product.name} 已加入购物车！`);
    },
    removeFromCart(productId) {
      this.cart = this.cart.filter(item => item.id !== productId);
    },
    showCart() {
      const modal = new bootstrap.Modal(document.getElementById('cartModal'));
      modal.show();
    },
    checkout() {
      alert('感谢购买！共计: ￥' + this.cartTotal.toFixed(2));
      this.cart = [];
    }
  },
  mounted() {
    this.username=localStorage.getItem('username')
    this.userId=localStorage.getItem('userId')
  }
};
</script>

<style scoped>
.product-card {
  transition: transform 0.2s;
}
.product-card:hover {
  transform: scale(1.05);
}
.cart-badge {
  position: absolute;
  top: -10px;
  right: -10px;
}
.carousel-item img {
  height: 400px;
  object-fit: cover;
}
footer {
  background-color: #f8f9fa;
  padding: 2rem 0;
  margin-top: 2rem;
}
</style>