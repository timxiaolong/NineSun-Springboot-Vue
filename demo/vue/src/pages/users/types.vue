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
              <a class="nav-link" href="/">首页</a>
            </li>
            <li class="nav-item">
              <a class="nav-link active" href="/types">分类</a>
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

    <!-- 分类页面内容 -->
    <div class="container my-5 flex-grow-1">
      <h2 class="mb-4 text-center">商品分类</h2>

      <!-- 筛选栏和搜索框 -->
      <div class="row mb-4">
        <div class="col-md-8">
          <div class="d-flex flex-wrap gap-2">
            <button
                v-for="(category,index) in category_id"
                :key="category"
                class="btn"
                :class="selectedCategory === category ? 'btn-primary' : 'btn-outline-primary'"
                @click="filterByCategory(index+1)"
            >
              {{ category }}
            </button>
            <button
                class="btn"
                :class="selectedCategory === '全部' ? 'btn-primary' : 'btn-outline-primary'"
                @click="filterByCategory('全部')"
            >
              全部
            </button>
          </div>
        </div>
        <div class="col-md-4">
          <div class="input-group">
            <input
                type="text"
                class="form-control"
                v-model="searchQuery"
                placeholder="搜索商品..."
                @input="filterProducts"
            >
            <button class="btn btn-outline-secondary" type="button" @click="clearSearch">清空</button>
          </div>
        </div>
      </div>

      <!-- 商品列表 -->
      <div v-if="filteredProducts.length === 0" class="alert alert-info text-center">
        未找到符合条件的商品
      </div>
      <div v-else class="row row-cols-1 row-cols-md-4 g-4">
        <div v-for="(product, index) in filteredProducts" :key="product.id" class="col">
          <div class="card product-card h-100">
            <img :src="product.picUrl" class="card-img-top" alt="商品图片">
            <div class="card-body">
              <h5 class="card-title">{{ product.name }}</h5>
              <p class="card-text">￥{{ product.price.toFixed(2) }}</p>
              <button class="btn btn-primary w-100" style="margin-bottom: 10px" @click="viewDetails(product.productId)">
                查看详情
              </button>
              <button class="btn btn-primary w-100" @click="addToCart(product.productId)">
                加入购物车
              </button>
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
  name: 'Category',
  data() {
    return {
      category_id: ['休闲食品','家清日化','米面粮油'],
      selectedCategory: '全部',
      searchQuery: '',
      username:'',
      userId:0,
      products: [
      ],
      filteredProducts: [],
      cartCount: 0 // 模拟购物车数量
    };
  },
  methods: {
    filterByCategory(category) {
      this.selectedCategory = category;
      this.filterProducts();
    },
    filterProducts() {
      let filtered = this.products;
      if (this.selectedCategory !== '全部') {
        filtered = filtered.filter(products => products.categoryId === this.selectedCategory);
      }
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase();
        filtered = filtered.filter(product => product.name.toLowerCase().includes(query));
      }
      this.filteredProducts = filtered;
    },
    viewDetails(productId){
      console.log(productId)
      this.$router.push({path:'/detial',query:{productId:productId}})
    },
    clearSearch() {
      this.searchQuery = '';
      this.filterProducts();
    },
    addToCart(productId) {
      if (localStorage.getItem('userId')==null){
        alert("请先登录")
        return
      }
      axios({
        method:'PUT',
        url:'http://localhost:8080/cart-items/addToCart',
        params:{
          userId:parseInt(localStorage.getItem('userId')),
          productId:productId,
          quantity:1,
          addedAt:new Date().getTime()
        }
      }).then(result=>{
        alert(result.data.message)
      })
    },
  },
  mounted() {
    // 初始化时显示所有商品
    this.username=localStorage.getItem('username')
    this.userId=localStorage.getItem('userId')
    axios({
      method:'GET',
      url:'http://localhost:8080/products/getproducts',
    }).then(result=>{
      this.products = result.data
      this.filteredProducts = [...this.products];
    })
    this.filterByCategory('全部')
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