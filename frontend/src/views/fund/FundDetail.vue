<template>
  <div class="fund-detail-page">
    <div class="page-header">
      <div class="header-left">
        <el-button @click="$router.push(`/fund/tabulate/${belong}`)" :icon="ArrowLeft">
          返回
        </el-button>
        <div>
          <h2 class="page-title">{{ fundName }}</h2>
          <p class="page-subtitle">{{ fundCode }} - {{ belong }}</p>
        </div>
      </div>
    </div>

    <el-card v-if="fundInfo" class="info-card">
      <div class="info-grid">
        <div class="info-item">
          <div class="label">持有份额</div>
          <div class="value">{{ fundInfo.tradeNum.toFixed(2) }}</div>
        </div>
        <div class="info-item">
          <div class="label">成本价</div>
          <div class="value">¥{{ fundInfo.costPrice.toFixed(4) }}</div>
        </div>
        <div class="info-item">
          <div class="label">当前价</div>
          <div class="value">¥{{ fundInfo.currentPrice?.toFixed(4) }}</div>
        </div>
        <div class="info-item">
          <div class="label">当前市值</div>
          <div class="value">¥{{ formatNumber(fundInfo.currentValue) }}</div>
        </div>
        <div class="info-item">
          <div class="label">收益</div>
          <div class="value" :class="profitClass(fundInfo.profit)">
            ¥{{ formatNumber(fundInfo.profit) }}
          </div>
        </div>
        <div class="info-item">
          <div class="label">收益率</div>
          <div class="value" :class="profitClass(fundInfo.profitRate)">
            {{ fundInfo.profitRate?.toFixed(2) }}%
          </div>
        </div>
      </div>
    </el-card>

    <el-card v-if="fundInfo && fundInfo.tradeList && fundInfo.tradeList.length > 0" class="trade-list-card">
      <template #header>
        <div class="card-header">
          <span>交易明细</span>
        </div>
      </template>
      <el-table :data="fundInfo.tradeList" stripe>
        <el-table-column prop="tradeDate" label="交易日期" width="120" />
        <el-table-column prop="tradeTime" label="交易时间" width="100" />
        <el-table-column prop="tradeType" label="交易类型" width="100">
          <template #default="{ row }">
            <el-tag
              :type="row.tradeType === '买入' ? 'danger' : 'success'"
              size="small"
            >
              {{ row.tradeType }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="tradePrice" label="交易价格" width="120" align="right">
          <template #default="{ row }">
            ¥{{ row.tradePrice.toFixed(4) }}
          </template>
        </el-table-column>
        <el-table-column prop="tradeNum" label="交易份额" width="120" align="right">
          <template #default="{ row }">
            {{ row.tradeNum.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="tradeAmount" label="交易金额" width="140" align="right">
          <template #default="{ row }">
            ¥{{ formatNumber(row.tradeAmount) }}
          </template>
        </el-table-column>
        <el-table-column prop="fee" label="手续费" width="100" align="right">
          <template #default="{ row }">
            ¥{{ row.fee.toFixed(2) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-else class="empty-card">
      <el-empty description="暂无交易记录" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { ArrowLeft } from '@element-plus/icons-vue';
import { getFundInfo } from '@/api/fund';
import type { FundTradeInfoVO } from '@/types/fund';

const route = useRoute();
const belong = route.params.belong as string;
const fundCode = route.params.fundCode as string;
const fundInfo = ref<FundTradeInfoVO | null>(null);
const fundName = ref('');
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
    const res = await getFundInfo(belong, fundCode);
    if (res.data) {
      fundInfo.value = res.data;
      fundName.value = res.data.fundName;
    }
  } catch (error) {
    console.error('Failed to load fund info:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.fund-detail-page {
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

.page-subtitle {
  font-size: var(--text-sm);
  color: var(--text-tertiary);
  margin: var(--spacing-1) 0 0;
}

.info-card {
  border: 1px solid var(--border-light);
  margin-bottom: var(--spacing-8);
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: var(--spacing-6);
}

.info-item {
  text-align: center;
  padding: var(--spacing-4);
  border-radius: var(--radius-md);
  background-color: var(--bg-secondary);
}

.info-item .label {
  font-size: var(--text-sm);
  color: var(--text-tertiary);
  margin-bottom: var(--spacing-2);
}

.info-item .value {
  font-size: var(--text-xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
}

.profit-positive {
  color: var(--color-green);
}

.profit-negative {
  color: var(--color-red);
}

.trade-list-card {
  border: 1px solid var(--border-light);
}

.card-header {
  font-weight: var(--font-weight-semibold);
}

.empty-card {
  border: 1px solid var(--border-light);
}
</style>
