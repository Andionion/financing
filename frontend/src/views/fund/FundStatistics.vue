<template>
  <div class="fund-statistics-page">
    <div class="page-header">
      <div class="header-left">
        <el-button @click="$router.push('/fund')" :icon="ArrowLeft">返回</el-button>
        <h2 class="page-title">{{ belong }} - 基金统计</h2>
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
          <div class="stat-label">基金数量</div>
          <div class="stat-value">{{ statistics.fundCount }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">盈利/亏损</div>
          <div class="stat-value">
            <span class="profit-text">{{ statistics.profitFundCount }}</span>
            /
            <span class="loss-text">{{ statistics.lossFundCount }}</span>
          </div>
        </div>
      </div>
    </el-card>

    <el-card v-if="statistics && statistics.fundDetails.length > 0" class="fund-list-card">
      <template #header>
        <div class="card-header">
          <span>持仓明细</span>
        </div>
      </template>
      <el-table :data="statistics.fundDetails" stripe>
        <el-table-column prop="fundCode" label="基金代码" width="120" />
        <el-table-column prop="fundName" label="基金名称" min-width="150" />
        <el-table-column prop="investAmount" label="投入金额" width="140" align="right">
          <template #default="{ row }">
            ¥{{ formatNumber(row.investAmount) }}
          </template>
        </el-table-column>
        <el-table-column prop="currentAmount" label="当前市值" width="140" align="right">
          <template #default="{ row }">
            ¥{{ formatNumber(row.currentAmount) }}
          </template>
        </el-table-column>
        <el-table-column prop="profit" label="收益" width="140" align="right">
          <template #default="{ row }">
            <span :class="profitClass(row.profit)">
              ¥{{ formatNumber(row.profit) }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="profitRate" label="收益率" width="120" align="right">
          <template #default="{ row }">
            <span :class="profitClass(row.profitRate)">
              {{ row.profitRate.toFixed(2) }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="proportion" label="占比" width="100" align="right">
          <template #default="{ row }">
            {{ row.proportion.toFixed(2) }}%
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <router-link :to="`/fund/${belong}/${row.fundCode}`">
              <el-button type="primary" size="small">详情</el-button>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-else class="empty-card">
      <el-empty description="暂无持仓数据" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { ArrowLeft } from '@element-plus/icons-vue';
import { getFundStatistics } from '@/api/fund';
import type { FundStatisticsVO } from '@/types/fund';

const route = useRoute();
const belong = route.params.belong as string;
const statistics = ref<FundStatisticsVO | null>(null);
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
    const res = await getFundStatistics(belong);
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
.fund-statistics-page {
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
  margin-bottom: var(--spacing-8);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
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

.profit-text {
  color: var(--color-green);
}

.loss-text {
  color: var(--color-red);
}

.fund-list-card {
  border: 1px solid var(--border-light);
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-weight: var(--font-weight-semibold);
}

.empty-card {
  border: 1px solid var(--border-light);
}
</style>
