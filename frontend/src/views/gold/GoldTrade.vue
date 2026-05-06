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
      <!-- Left: Trade Form -->
      <el-card class="form-card">
        <template #header>
          <div class="card-header">
            <span>{{ editId ? '编辑交易' : '新增交易' }}</span>
          </div>
        </template>
        <el-form :model="form" label-width="100px" @submit.prevent="handleSubmit">
          <el-form-item label="交易类型" required>
            <el-select v-model="form.tradeType" placeholder="请选择" style="width: 100%">
              <el-option label="买入" value="买入" />
              <el-option label="卖出" value="卖出" />
            </el-select>
          </el-form-item>
          <el-form-item label="交易价格" required>
            <el-input-number
              v-model="form.tradePrice"
              :precision="4"
              :min="0"
              :controls="false"
              style="width: 100%"
              placeholder="请输入价格"
            />
          </el-form-item>
          <el-form-item label="交易克数" required>
            <el-input-number
              v-model="form.tradeNum"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
              placeholder="请输入克数"
            />
          </el-form-item>
          <el-form-item label="手续费">
            <el-input-number
              v-model="form.fee"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
              placeholder="请输入手续费"
            />
          </el-form-item>
          <el-form-item label="交易日期" required>
            <el-date-picker
              v-model="form.tradeDate"
              type="date"
              placeholder="选择日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="交易时间">
            <el-time-picker
              v-model="form.tradeTime"
              format="HH:mm:ss"
              value-format="HH:mm:ss"
              placeholder="选择时间"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="备注">
            <el-input
              v-model="form.remark"
              type="textarea"
              :rows="3"
              placeholder="请输入备注"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmit" :loading="submitting">
              {{ editId ? '更新' : '提交' }}
            </el-button>
            <el-button v-if="editId" @click="resetForm">取消</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- Right: Trade List -->
      <el-card class="list-card">
        <template #header>
          <div class="card-header">
            <span>交易记录</span>
            <el-button @click="loadData" :icon="Refresh">刷新</el-button>
          </div>
        </template>
        <el-table :data="tradeList" stripe v-loading="loading">
          <el-table-column prop="tradeDate" label="日期" width="120" />
          <el-table-column prop="tradeType" label="类型" width="80">
            <template #default="{ row }">
              <el-tag :type="row.tradeType === '买入' ? 'danger' : 'success'" size="small">
                {{ row.tradeType }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="tradePrice" label="价格" width="100" align="right">
            <template #default="{ row }">¥{{ row.tradePrice.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="tradeNum" label="克数" width="100" align="right">
            <template #default="{ row }">{{ row.tradeNum.toFixed(2) }}g</template>
          </el-table-column>
          <el-table-column prop="fee" label="手续费" width="90" align="right">
            <template #default="{ row }">¥{{ row.fee.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" min-width="120" show-overflow-tooltip />
          <el-table-column label="操作" width="150" fixed="right">
            <template #default="{ row }">
              <el-button size="small" @click="handleEdit(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
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
const submitting = ref(false);
const editId = ref<number | null>(null);

const form = ref<GoldTradeFormDTO>({
  tradeType: '买入',
  tradePrice: 0,
  tradeNum: 0,
  tradeDate: new Date().toISOString().split('T')[0],
  tradeTime: new Date().toTimeString().split(' ')[0],
  fee: 0,
  remark: '',
});

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getGoldList({ page: 1, size: 1000 });
    if (res.data && res.data.records) {
      tradeList.value = res.data.records;
    }
  } catch (error) {
    console.error('Failed to load gold list:', error);
  } finally {
    loading.value = false;
  }
};

const handleSubmit = async () => {
  if (!form.value.tradeType || !form.value.tradePrice || !form.value.tradeNum || !form.value.tradeDate) {
    ElMessage.warning('请填写必填项');
    return;
  }

  submitting.value = true;
  try {
    if (editId.value) {
      await updateGoldTrade(editId.value, form.value);
      ElMessage.success('更新成功');
    } else {
      await addGoldTrade(form.value);
      ElMessage.success('添加成功');
    }
    resetForm();
    loadData();
  } catch (error) {
    ElMessage.error('操作失败');
  } finally {
    submitting.value = false;
  }
};

const handleEdit = (row: GoldTradeVO) => {
  editId.value = row.id;
  form.value = {
    tradeType: row.tradeType,
    tradePrice: row.tradePrice,
    tradeNum: row.tradeNum,
    tradeDate: row.tradeDate,
    tradeTime: row.tradeTime,
    fee: row.fee,
    remark: row.remark,
  };
};

const resetForm = () => {
  editId.value = null;
  form.value = {
    tradeType: '买入',
    tradePrice: 0,
    tradeNum: 0,
    tradeDate: new Date().toISOString().split('T')[0],
    tradeTime: new Date().toTimeString().split(' ')[0],
    fee: 0,
    remark: '',
  };
};

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除这条记录吗？', '确认删除', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });
    await deleteGoldTrade(id);
    ElMessage.success('删除成功');
    loadData();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败');
    }
  }
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

@media (max-width: 1024px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
