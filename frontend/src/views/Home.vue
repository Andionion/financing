<template>
  <div class="home-page">
    <div class="page-header">
      <h2 class="page-title">金融管理助手</h2>
      <p class="page-description">管理您的基金、黄金和公积金投资，实时跟踪收益情况</p>
    </div>

    <div class="module-grid">
      <!-- 基金模块卡片 -->
      <el-card class="module-card" shadow="hover">
        <router-link to="/fund" class="card-link">
          <div class="module-icon fund-icon">
            <el-icon :size="48"><TrendCharts /></el-icon>
          </div>
          <h3 class="module-title">基金交易</h3>
          <p class="module-desc">
            管理基金投资，查看持仓明细和收益统计
          </p>
          <div class="module-stats">
            <div class="stat-item">
              <span class="stat-label">持仓基金</span>
              <span class="stat-value">{{ fundCount }} 只</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">总收益</span>
              <span class="stat-value" :class="profitClass(fundProfit)">
                ¥{{ formatNumber(fundProfit) }}
              </span>
            </div>
          </div>
          <div class="card-action">
            <el-button type="primary" size="default">
              查看详情
              <el-icon class="el-icon--right"><ArrowRight /></el-icon>
            </el-button>
          </div>
        </router-link>
      </el-card>

      <!-- 黄金模块卡片 -->
      <el-card class="module-card" shadow="hover">
        <router-link to="/gold" class="card-link">
          <div class="module-icon gold-icon">
            <el-icon :size="48"><Coin /></el-icon>
          </div>
          <h3 class="module-title">黄金交易</h3>
          <p class="module-desc">
            跟踪黄金投资，实时计算买卖收益
          </p>
          <div class="module-stats">
            <div class="stat-item">
              <span class="stat-label">持仓克数</span>
              <span class="stat-value">{{ goldGrams }} g</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">总收益</span>
              <span class="stat-value" :class="profitClass(goldProfit)">
                ¥{{ formatNumber(goldProfit) }}
              </span>
            </div>
          </div>
          <div class="card-action">
            <el-button type="warning" size="default">
              查看详情
              <el-icon class="el-icon--right"><ArrowRight /></el-icon>
            </el-button>
          </div>
        </router-link>
      </el-card>

      <!-- 公积金模块卡片 -->
      <el-card class="module-card" shadow="hover">
        <router-link to="/hpf" class="card-link">
          <div class="module-icon hpf-icon">
            <el-icon :size="48"><House /></el-icon>
          </div>
          <h3 class="module-title">公积金管理</h3>
          <p class="module-desc">
            记录公积金缴存，查看余额和明细
          </p>
          <div class="module-stats">
            <div class="stat-item">
              <span class="stat-label">当前余额</span>
              <span class="stat-value">¥{{ formatNumber(hpfBalance) }}</span>
            </div>
            <div class="stat-item">
              <span class="stat-label">缴存月数</span>
              <span class="stat-value">{{ hpfMonths }} 月</span>
            </div>
          </div>
          <div class="card-action">
            <el-button type="success" size="default">
              查看详情
              <el-icon class="el-icon--right"><ArrowRight /></el-icon>
            </el-button>
          </div>
        </router-link>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { TrendCharts, Coin, House, ArrowRight } from '@element-plus/icons-vue';
import { getFundIndex } from '@/api/fund';
import { getGoldStatistics } from '@/api/gold';
import { getHpfStatistics } from '@/api/hpf';

const fundCount = ref(0);
const fundProfit = ref(0);
const goldGrams = ref(0);
const goldProfit = ref(0);
const hpfBalance = ref(0);
const hpfMonths = ref(0);
const loading = ref(false);

onMounted(async () => {
  loading.value = true;
  try {
    // Load fund data
    const fundRes = await getFundIndex();
    if (fundRes.data && fundRes.data.list) {
      fundCount.value = fundRes.data.list.reduce(
        (sum, item) => sum + item.fundList.length,
        0
      );
      fundProfit.value = fundRes.data.list.reduce(
        (sum, item) => sum + item.totalProfit,
        0
      );
    }

    // Load gold data
    const goldRes = await getGoldStatistics();
    if (goldRes.data) {
      goldGrams.value = goldRes.data.currentGoldNum;
      goldProfit.value = goldRes.data.totalProfit;
    }

    // Load HPF data
    const hpfRes = await getHpfStatistics();
    if (hpfRes.data) {
      hpfBalance.value = hpfRes.data.currentBalance;
      hpfMonths.value = hpfRes.data.monthCount;
    }
  } catch (error) {
    console.error('Failed to load home data:', error);
  } finally {
    loading.value = false;
  }
});

const formatNumber = (num: number): string => {
  return num.toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};

const profitClass = (profit: number): string => {
  return profit >= 0 ? 'profit-positive' : 'profit-negative';
};
</script>

<style scoped>
.home-page {
  max-width: 1200px;
}

.page-header {
  margin-bottom: var(--spacing-12);
}

.page-title {
  font-family: var(--font-family-display);
  font-size: var(--text-4xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--spacing-2);
  letter-spacing: var(--tracking-tight);
}

.page-description {
  font-size: var(--text-lg);
  color: var(--text-secondary);
  margin: 0;
  line-height: var(--leading-relaxed);
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(340px, 1fr));
  gap: var(--spacing-8);
}

.module-card {
  height: 100%;
  transition: all var(--transition-normal);
  border: 1px solid var(--border-light);
}

.module-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-lg);
}

.card-link {
  text-decoration: none;
  color: inherit;
  display: block;
  padding: var(--spacing-6);
}

.module-icon {
  width: 72px;
  height: 72px;
  border-radius: var(--radius-xl);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: var(--spacing-6);
}

.fund-icon {
  background-color: rgba(0, 102, 255, 0.1);
  color: var(--color-blue);
}

.gold-icon {
  background-color: rgba(244, 144, 21, 0.1);
  color: var(--color-yellow);
}

.hpf-icon {
  background-color: rgba(0, 138, 40, 0.1);
  color: var(--color-green);
}

.module-title {
  font-family: var(--font-family-display);
  font-size: var(--text-2xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--spacing-2);
}

.module-desc {
  font-size: var(--text-base);
  color: var(--text-secondary);
  margin: 0 0 var(--spacing-6);
  line-height: var(--leading-normal);
}

.module-stats {
  display: flex;
  gap: var(--spacing-8);
  padding: var(--spacing-4) 0;
  border-top: 1px solid var(--border-light);
  border-bottom: 1px solid var(--border-light);
  margin-bottom: var(--spacing-6);
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-1);
}

.stat-label {
  font-size: var(--text-sm);
  color: var(--text-tertiary);
}

.stat-value {
  font-size: var(--text-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--text-primary);
}

.profit-positive {
  color: var(--color-green);
}

.profit-negative {
  color: var(--color-red);
}

.card-action {
  display: flex;
  justify-content: flex-start;
}

@media (max-width: 768px) {
  .module-grid {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: var(--text-3xl);
  }

  .module-stats {
    flex-direction: column;
    gap: var(--spacing-4);
  }
}
</style>
