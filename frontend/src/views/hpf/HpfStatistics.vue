<template>
  <div class="hpf-statistics-page">
    <div class="page-header">
      <div class="header-left">
        <el-button @click="$router.push('/hpf')" :icon="ArrowLeft">返回</el-button>
        <h2 class="page-title">公积金统计</h2>
      </div>
    </div>

    <el-card v-if="statistics" class="stats-card">
      <div class="stats-grid">
        <div class="stat-item">
          <div class="stat-label">个人缴存总额</div>
          <div class="stat-value">¥{{ formatNumber(statistics.totalDeposit) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">公司缴存总额</div>
          <div class="stat-value">¥{{ formatNumber(statistics.totalCompany) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">缴存总额</div>
          <div class="stat-value">¥{{ formatNumber(statistics.totalAmount) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">当前余额</div>
          <div class="stat-value">¥{{ formatNumber(statistics.currentBalance) }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">缴存次数</div>
          <div class="stat-value">{{ statistics.recordCount }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">缴存月数</div>
          <div class="stat-value">{{ statistics.monthCount }}</div>
        </div>
        <div class="stat-item">
          <div class="stat-label">月均缴存</div>
          <div class="stat-value">¥{{ formatNumber(statistics.monthlyAverage) }}</div>
        </div>
      </div>
    </el-card>

    <el-card v-if="statistics && statistics.monthlyDetails.length > 0" class="monthly-card">
      <template #header>
        <div class="card-header">
          <span>月度明细</span>
        </div>
      </template>
      <el-table :data="statistics.monthlyDetails" stripe>
        <el-table-column prop="month" label="月份" width="120" />
        <el-table-column prop="personalAmount" label="个人" width="140" align="right">
          <template #default="{ row }">¥{{ row.personalAmount.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="companyAmount" label="公司" width="140" align="right">
          <template #default="{ row }">¥{{ row.companyAmount.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="totalAmount" label="总额" width="140" align="right">
          <template #default="{ row }">¥{{ row.totalAmount.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="balance" label="余额" width="140" align="right">
          <template #default="{ row }">¥{{ row.balance.toFixed(2) }}</template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-else class="empty-card">
      <el-empty description="暂无统计数据" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ArrowLeft } from '@element-plus/icons-vue';
import { getHpfStatistics } from '@/api/hpf';
import type { HpfStatisticsVO } from '@/types/hpf';

const statistics = ref<HpfStatisticsVO | null>(null);
const loading = ref(false);

const formatNumber = (num: number): string => {
  return num.toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getHpfStatistics();
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
.hpf-statistics-page {
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

.monthly-card {
  border: 1px solid var(--border-light);
}

.card-header {
  font-weight: var(--font-weight-semibold);
}

.empty-card {
  border: 1px solid var(--border-light);
}
</style>
