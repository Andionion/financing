<template>
  <div class="fund-index-page">
    <div class="page-header">
      <h2 class="page-title">基金交易</h2>
      <p class="page-subtitle">选择账户查看基金持仓和交易明细</p>
    </div>

    <el-card v-if="ownerList.length > 0" class="owner-list-card">
      <div class="owner-grid">
        <el-card
          v-for="owner in ownerList"
          :key="owner"
          class="owner-card"
          shadow="hover"
        >
          <router-link :to="`/fund/tabulate/${owner}`" class="owner-link">
            <div class="owner-info">
              <div class="owner-avatar">
                {{ owner.charAt(0).toUpperCase() }}
              </div>
              <div class="owner-details">
                <h3 class="owner-name">{{ owner }}</h3>
                <p class="owner-desc">点击查看持仓详情</p>
              </div>
            </div>
            <el-button type="primary" size="small">
              查看详情
              <el-icon class="el-icon--right"><ArrowRight /></el-icon>
            </el-button>
          </router-link>
        </el-card>
      </div>
    </el-card>

    <el-card v-else class="empty-card">
      <el-empty description="暂无可用账户">
        <el-button type="primary" @click="loadData">刷新</el-button>
      </el-empty>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ArrowRight } from '@element-plus/icons-vue';
import { getOwners } from '@/api/fund';

const ownerList = ref<string[]>([]);
const loading = ref(false);

const loadData = async () => {
  loading.value = true;
  try {
    const res = await getOwners();
    if (res.data) {
      ownerList.value = res.data;
    }
  } catch (error) {
    console.error('Failed to load owners:', error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.fund-index-page {
  max-width: 1200px;
}

.page-header {
  margin-bottom: var(--spacing-8);
}

.page-title {
  font-family: var(--font-family-display);
  font-size: var(--text-3xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--spacing-2);
}

.page-subtitle {
  font-size: var(--text-base);
  color: var(--text-secondary);
  margin: 0;
}

.owner-list-card {
  border: 1px solid var(--border-light);
}

.owner-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: var(--spacing-6);
}

.owner-card {
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
}

.owner-card:hover {
  border-color: var(--color-blue);
  box-shadow: var(--shadow-md);
}

.owner-link {
  text-decoration: none;
  color: inherit;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--spacing-4);
}

.owner-info {
  display: flex;
  align-items: center;
  gap: var(--spacing-4);
}

.owner-avatar {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-full);
  background-color: var(--color-blue);
  color: var(--text-inverse);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: var(--text-xl);
  font-weight: var(--font-weight-bold);
}

.owner-name {
  font-size: var(--text-lg);
  font-weight: var(--font-weight-semibold);
  color: var(--text-primary);
  margin: 0;
}

.owner-desc {
  font-size: var(--text-sm);
  color: var(--text-tertiary);
  margin: var(--spacing-1) 0 0;
}

.empty-card {
  border: 1px solid var(--border-light);
}
</style>
