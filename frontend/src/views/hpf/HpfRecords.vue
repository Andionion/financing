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
        <el-button @click="handleAdd" type="success">
          <el-icon><Plus /></el-icon>
          新增记录
        </el-button>
      </div>
    </div>

    <div class="content-grid">
      <!-- Left: Form -->
      <el-card v-if="showForm" class="form-card">
        <template #header>
          <div class="card-header">
            <span>{{ editId ? '编辑记录' : '新增记录' }}</span>
          </div>
        </template>
        <el-form :model="form" label-width="100px" @submit.prevent="handleSubmit">
          <el-form-item label="所属账户" required>
            <el-input v-model="form.belong" placeholder="请输入账户" />
          </el-form-item>
          <el-form-item label="缴存月份" required>
            <el-date-picker
              v-model="form.month"
              type="month"
              placeholder="选择月份"
              format="YYYY-MM"
              value-format="YYYY-MM"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="缴存日期" required>
            <el-date-picker
              v-model="form.depositDate"
              type="date"
              placeholder="选择日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="个人缴存" required>
            <el-input-number
              v-model="form.depositAmount"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="公司缴存" required>
            <el-input-number
              v-model="form.companyAmount"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="总额" required>
            <el-input-number
              v-model="form.totalAmount"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="余额" required>
            <el-input-number
              v-model="form.balance"
              :precision="2"
              :min="0"
              :controls="false"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmit" :loading="submitting">
              {{ editId ? '更新' : '提交' }}
            </el-button>
            <el-button @click="showForm = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- Right: List -->
      <el-card class="list-card">
        <template #header>
          <div class="card-header">
            <span>缴存记录</span>
            <el-button @click="loadData" :icon="Refresh">刷新</el-button>
          </div>
        </template>
        <el-table :data="recordList" stripe v-loading="loading">
          <el-table-column prop="belong" label="账户" width="120" />
          <el-table-column prop="month" label="月份" width="100" />
          <el-table-column prop="depositDate" label="缴存日期" width="120" />
          <el-table-column prop="depositAmount" label="个人" width="100" align="right">
            <template #default="{ row }">¥{{ row.depositAmount.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="companyAmount" label="公司" width="100" align="right">
            <template #default="{ row }">¥{{ row.companyAmount.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="totalAmount" label="总额" width="120" align="right">
            <template #default="{ row }">¥{{ row.totalAmount.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="balance" label="余额" width="120" align="right">
            <template #default="{ row }">¥{{ row.balance.toFixed(2) }}</template>
          </el-table-column>
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
import { DataAnalysis, Plus, Refresh } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { getHpfList, addHpfRecord, updateHpfRecord, deleteHpfRecord } from '@/api/hpf';
import type { HpfRecordVO, HpfRecordFormDTO } from '@/types/hpf';

const recordList = ref<HpfRecordVO[]>([]);
const loading = ref(false);
const submitting = ref(false);
const editId = ref<number | null>(null);
const showForm = ref(true);

const form = ref<HpfRecordFormDTO>({
  belong: '',
  month: '',
  depositDate: '',
  depositAmount: 0,
  companyAmount: 0,
  totalAmount: 0,
  balance: 0,
});

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getHpfList({ page: 1, size: 1000 });
    if (res.data && res.data.records) {
      recordList.value = res.data.records;
    }
  } catch (error) {
    console.error('Failed to load HPF list:', error);
  } finally {
    loading.value = false;
  }
};

const handleSubmit = async () => {
  if (!form.value.belong || !form.value.month || !form.value.depositDate) {
    ElMessage.warning('请填写必填项');
    return;
  }

  submitting.value = true;
  try {
    if (editId.value) {
      await updateHpfRecord(editId.value, form.value);
      ElMessage.success('更新成功');
    } else {
      await addHpfRecord(form.value);
      ElMessage.success('添加成功');
    }
    showForm.value = false;
    resetForm();
    loadData();
  } catch (error) {
    ElMessage.error('操作失败');
  } finally {
    submitting.value = false;
  }
};

const handleAdd = () => {
  resetForm();
  editId.value = null;
  showForm.value = true;
};

const handleEdit = (row: HpfRecordVO) => {
  editId.value = row.id;
  form.value = {
    belong: row.belong,
    month: row.month,
    depositDate: row.depositDate,
    depositAmount: row.depositAmount,
    companyAmount: row.companyAmount,
    totalAmount: row.totalAmount,
    balance: row.balance,
  };
  showForm.value = true;
};

const resetForm = () => {
  editId.value = null;
  form.value = {
    belong: '',
    month: '',
    depositDate: '',
    depositAmount: 0,
    companyAmount: 0,
    totalAmount: 0,
    balance: 0,
  };
};

const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除这条记录吗？', '确认删除', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });
    await deleteHpfRecord(id);
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
.hpf-records-page {
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

.header-actions {
  display: flex;
  gap: var(--spacing-2);
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
