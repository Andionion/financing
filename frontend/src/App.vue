<template>
  <div class="app-container">
    <el-container>
      <el-header class="app-header">
        <div class="header-content">
          <router-link to="/" class="logo-link">
            <h1 class="logo">Financing</h1>
          </router-link>
          <el-menu
            :default-active="activeMenu"
            mode="horizontal"
            :ellipsis="false"
            class="main-menu"
            router
          >
            <el-menu-item index="/">首页</el-menu-item>
            <el-menu-item index="/fund">基金</el-menu-item>
            <el-menu-item index="/gold">黄金</el-menu-item>
            <el-menu-item index="/hpf">公积金</el-menu-item>
          </el-menu>
        </div>
      </el-header>
      <el-main class="app-main">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const activeMenu = computed(() => route.path);
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  background-color: var(--bg-secondary);
}

.app-header {
  background-color: var(--bg-primary);
  border-bottom: 1px solid var(--border-light);
  padding: 0;
  height: 60px;
  position: sticky;
  top: 0;
  z-index: var(--z-sticky);
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  gap: var(--spacing-8);
  padding: 0 var(--spacing-6);
}

.logo-link {
  text-decoration: none;
}

.logo {
  font-family: var(--font-family-display);
  font-size: var(--text-2xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0;
  letter-spacing: var(--tracking-tight);
}

.main-menu {
  flex: 1;
  border-bottom: none !important;
}

.app-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-8) var(--spacing-6);
}

/* Page transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity var(--transition-normal);
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
