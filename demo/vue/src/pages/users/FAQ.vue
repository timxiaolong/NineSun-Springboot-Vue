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

    <!-- 帮助中心内容 -->
    <div class="container my-5 flex-grow-1">
      <h2 class="mb-4 text-center">帮助中心</h2>
      <!-- 搜索框 -->
      <div class="mb-4">
        <div class="input-group">
          <input
              type="text"
              class="form-control"
              v-model="searchQuery"
              placeholder="搜索常见问题..."
              @input="filterFAQs"
          >
          <button class="btn btn-outline-secondary" type="button" @click="clearSearch">清空</button>
        </div>
      </div>
      <!-- FAQ 折叠面板 -->
      <div class="accordion" id="faqAccordion">
        <div class="accordion-item" v-for="faq in filteredFAQs" :key="faq.id">
          <h2 class="accordion-header">
            <button
                class="accordion-button collapsed"
                type="button"
                data-bs-toggle="collapse"
                :data-bs-target="`#faq${faq.id}`"
                :aria-controls="`faq${faq.id}`"
            >
              {{ faq.question }}
            </button>
          </h2>
          <div
              :id="`faq${faq.id}`"
              class="accordion-collapse collapse"
              :aria-labelledby="`heading${faq.id}`"
          >
            <div class="accordion-body">
              {{ faq.answer }}
            </div>
          </div>
        </div>
        <div v-if="filteredFAQs.length === 0" class="text-center text-muted mt-4">
          未找到相关问题
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
import * as bootstrap from 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js';

export default {
  name: 'HelpCenter',
  data() {
    return {
      cartCount: 0, // 模拟购物车数量
      searchQuery: '',
      faqs: [
        {
          id: 1,
          question: '如何下单购买商品？',
          answer: '在首页或分类页面选择商品，点击“加入购物车”，然后前往购物车页面确认订单信息，选择收货地址和支付方式完成下单。'
        },
        {
          id: 2,
          question: '支持哪些支付方式？',
          answer: '我们支持支付宝、微信支付、银联卡等多种支付方式，具体选项在支付页面显示。'
        },
        {
          id: 3,
          question: '如何查看订单状态？',
          answer: '登录后，在个人中心点击“订单列表”查看所有订单，点击具体订单可查看详细状态，如待支付、待发货、已完成等。'
        },
        {
          id: 4,
          question: '可以退货或换货吗？',
          answer: '支持7天无理由退货，商品需保持完好。请在个人中心订单页面申请退货，客服会在1-3个工作日内处理。'
        },
        {
          id: 5,
          question: '如何联系客服？',
          answer: '您可以通过页面底部的联系方式（邮箱：support@onlineshop.com，电话：400-123-4567）联系我们，或使用在线客服聊天功能。'
        },
        {
          id: 6,
          question: '商品缺货怎么办？',
          answer: '若商品显示缺货，您可在商品详情页点击“到货通知”，我们会在补货后通知您。'
        }
      ],
      filteredFAQs: []
    };
  },
  computed: {
    // 可选：如果需要更复杂的搜索逻辑，可以使用计算属性
  },
  methods: {
    filterFAQs() {
      const query = this.searchQuery.toLowerCase();
      this.filteredFAQs = this.faqs.filter(
          faq => faq.question.toLowerCase().includes(query) || faq.answer.toLowerCase().includes(query)
      );
    },
    clearSearch() {
      this.searchQuery = '';
      this.filteredFAQs = [...this.faqs];
    },
    goToCart() {
      alert('跳转到购物车页面');
      // 实际应用中可使用路由跳转
    }
  },
  mounted() {
    // 初始化时显示所有FAQ
    this.filteredFAQs = [...this.faqs];
  }
};
</script>

<style scoped>
.accordion-button {
  font-weight: 500;
}
.accordion-button:not(.collapsed) {
  background-color: #e9ecef;
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