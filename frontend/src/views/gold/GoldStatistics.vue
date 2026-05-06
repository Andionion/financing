<template>
  <div class="gold-statistics-page">
    <div class="page-header">
      <div class="header-left">
        <el-button @click="$router.push('/gold')" :icon="ArrowLeft">返回</el-button>
        <h2 class="page-title">黄金统计</h2>
      </div>
    </div>

    <el-card v-if="statistics" class="stats-card">
      <div class="stats-grid">
        <div class="stat-item">
          <div class="stat-label">总投入</div>
          <div class="stat-value">¥{{ formatNumber(statistics.totalInvest) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">当前市值</div>
          <div class="stat-value">¥{{ formatNumber(statistics.totalCurrent) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">总收益</div>
          <div class="stat-value" :class="profitClass(statistics.totalProfit)">
            ¥{{ formatNumber(statistics.totalProfit) }}
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-label">收益率</div>
          <div class="stat-value" :class="profitClass(statistics.totalProfitRate)">
            {{ statistics.totalProfitRate.toFixed(2) }}%
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-label">持仓克数</div>
          <div class="stat-value">{{ statistics.currentGoldNum.toFixed(2) }}g</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">当前金价</div>
          <div class="stat-value">¥{{ statistics.currentGoldPrice?.toFixed(2) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">交易次数</div>
          <div class="stat-value">{{ statistics.tradeCount }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">买入/卖出</div>
          <div class="stat-value">
            <span class="buy-text">{{ statistics.buyCount }}</span>
            /
            <span class="sell-text">{{ statistics.sellCount }}</span>
          </div>
        </div>
      </div>
    </el-card>

    <el-card v-else class="empty-card">
      <el-empty description="暂无统计数据" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ArrowLeft } from '@element-plus/icons-vue';
import { getGoldStatistics } from '@/api/gold';
import type { GoldStatisticsVO } from '@/types/gold';

const statistics = ref<GoldStatisticsVO | null>(null);
const loading = ref(false);

const formatNumber = (num: number): string => {
  return num.toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};

const profitClass = (value: number): string => {
  return value >= 0 ? 'profit-positive' : 'profit-negative';
};

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getGoldStatistics();
    if (res.data) {
      statistics.value = res.data;
    }
  } catch (error) {
    console.error('Failed to load statistics:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.gold-statistics-page {
  max-width: 1200px;
}

.page-header {
  margin-bottom: var(--spacing-8);
}

.header-left {
  display: flex;
  align-items: center;
  gap: var(--spacing-4);
}

.page-title {
  font-family: var(--font-family-display);
  font-size: var(--text-3xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.stats-card {
  border: 1px solid var(--border-light);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--spacing-6);
}

.stat-item {
  text-align: center;
  padding: var(--spacing-4);
  border-radius: var(--radius-md);
  background-color: var(--bg-secondary);
}

.stat-label {
  font-size: var(--text-sm);
  color: var(--text-tertiary);
  margin-bottom: var(--spacing-2);
}

.stat-value {
  font-size: var(--text-2xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
}

.profit-positive {
  color: var(--color-green);
}

.profit-negative {
  color: var(--color-red);
}

.buy-text {
  color: var(--color-red);
}

.sell-text {
  color: var(--color-green);
}

.empty-card {
  border: 1px solid var(--border-light);
}
</style>
