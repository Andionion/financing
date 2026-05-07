<template>
  <div class="gold-trade-page">
    <div class="page-header">
      <div class="header-left">
        <h2 class="page-title">黄金交易</h2>
      </div>
      <el-button @click="$router.push('/gold/tabulate')" type="primary">
        <el-icon><DataAnalysis /></el-icon>
        查看统计
      </el-button>
    </div>

    <div class="content-grid">
      <!-- Trade List -->
      <el-card class="list-card" style="width: 100%">
        <template #header>
          <div class="card-header">
            <span>交易记录</span>
            <el-button @click="loadData" :icon="Refresh">刷新</el-button>
          </div>
        </template>
        <el-table :data="tradeList" stripe v-loading="loading" style="width: 100%">
          <el-table-column prop="tradeDate" label="日期" width="120" align="center" />
          <el-table-column prop="tradeType" label="类型" width="100" align="center">
            <template #default="{ row }">
              <el-tag :type="row.tradeType === 'purchase' ? 'danger' : 'success'" size="small">
                {{ row.tradeType === 'purchase' ? '买入' : '卖出' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="goldType" label="黄金类型" width="100" align="center">
            <template #default="{ row }">
              {{ row.goldType === 'paper' ? '纸面金' : '实体金' }}
            </template>
          </el-table-column>
          <el-table-column prop="unitPrice" label="单价" min-width="120" align="right">
            <template #default="{ row }">¥{{ row.unitPrice.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="weight" label="重量" min-width="100" align="right">
            <template #default="{ row }">{{ row.weight.toFixed(2) }}g</template>
          </el-table-column>
          <el-table-column prop="amount" label="金额" min-width="120" align="right">
            <template #default="{ row }">¥{{ row.amount.toFixed(2) }}</template>
          </el-table-column>
        </el-table>
        
        <!-- 分页组件 -->
        <div class="pagination-wrapper">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { DataAnalysis, Refresh } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { getGoldList, addGoldTrade, updateGoldTrade, deleteGoldTrade } from '@/api/gold';
import type { GoldTradeVO, GoldTradeFormDTO } from '@/types/gold';

const tradeList = ref<GoldTradeVO[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = ref(20);
const total = ref(0);

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getGoldList({ page: currentPage.value, size: pageSize.value });
    if (res.data && res.data.records) {
      tradeList.value = res.data.records;
      total.value = res.data.total || 0;
    }
  } catch (error) {
    console.error('Failed to load gold list:', error);
  } finally {
    loading.value = false;
  }
};

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  currentPage.value = 1;
  loadData();
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
  loadData();
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.gold-trade-page {
  max-width: 1200px;
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--spacing-8);
}

.page-title {
  font-family: var(--font-family-display);
  font-size: var(--text-3xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.content-grid {
  display: grid;
  grid-template-columns: 400px 1fr;
  gap: var(--spacing-8);
}

.form-card,
.list-card {
  border: 1px solid var(--border-light);
}

.card-header {
  font-weight: var(--font-weight-semibold);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
  padding: 10px 0;
}

@media (max-width: 1024px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
