<template>
  <div class="hpf-records-page">
    <div class="page-header">
      <div class="header-left">
        <h2 class="page-title">公积金管理</h2>
      </div>
      <div class="header-actions">
        <el-button @click="$router.push('/hpf/tabulate')" type="primary">
          <el-icon><DataAnalysis /></el-icon>
          查看统计
        </el-button>
      </div>
    </div>

    <div class="content-grid">
      <!-- List -->
      <el-card class="list-card">
        <template #header>
          <div class="card-header">
            <span>缴存记录</span>
            <el-button @click="loadData" :icon="Refresh">刷新</el-button>
          </div>
        </template>
        <el-table 
          :data="recordList" 
          stripe 
          v-loading="loading" 
          style="width: 100%"
          :header-cell-style="{ background: '#fafafa', color: '#606266', fontWeight: '600' }"
          border
        >
          <el-table-column prop="operationDate" label="操作日期" min-width="150" align="center" />
          <el-table-column prop="operationType" label="操作类型" width="120" align="center">
            <template #default="{ row }">
              <el-tag :type="row.operationType === 'deposit' ? 'success' : row.operationType === 'withdrawal' ? 'danger' : 'warning'">
                {{ row.operationType === 'deposit' ? '缴存' : row.operationType === 'withdrawal' ? '提取' : '利息' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="金额" min-width="150" align="right">
            <template #default="{ row }">¥{{ row.amount.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="balance" label="余额" min-width="150" align="right">
            <template #default="{ row }">¥{{ row.balance.toFixed(2) }}</template>
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
import { getHpfList } from '@/api/hpf';
import type { HpfRecordVO } from '@/types/hpf';

const recordList = ref<HpfRecordVO[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = ref(20);
const total = ref(0);

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getHpfList({ page: currentPage.value, size: pageSize.value });
    if (res.data && res.data.records) {
      recordList.value = res.data.records;
      total.value = res.data.total || 0;
    }
  } catch (error) {
    console.error('Failed to load HPF list:', error);
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
.hpf-records-page {
  padding: 20px;
  min-height: 100vh;
  background-color: #f5f7fa;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 0 4px;
}

.page-title {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: #303133;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.content-grid {
  display: grid;
  gap: 20px;
  width: 100%;
}

.list-card {
  width: 100%;
}

.list-card :deep(.el-card__body) {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
  padding: 10px 0;
}
</style>
