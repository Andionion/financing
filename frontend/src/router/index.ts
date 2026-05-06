import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
  },
  // Fund Routes
  {
    path: '/fund',
    name: 'FundIndex',
    component: () => import('@/views/fund/FundIndex.vue'),
  },
  {
    path: '/fund/tabulate/:belong',
    name: 'FundStatistics',
    component: () => import('@/views/fund/FundStatistics.vue'),
    props: true,
  },
  {
    path: '/fund/:belong/:fundCode',
    name: 'FundDetail',
    component: () => import('@/views/fund/FundDetail.vue'),
    props: true,
  },
  // Gold Routes
  {
    path: '/gold',
    name: 'Gold',
    component: () => import('@/views/gold/GoldTrade.vue'),
  },
  {
    path: '/gold/tabulate',
    name: 'GoldStatistics',
    component: () => import('@/views/gold/GoldStatistics.vue'),
  },
  // HPF Routes
  {
    path: '/hpf',
    name: 'Hpf',
    component: () => import('@/views/hpf/HpfRecords.vue'),
  },
  {
    path: '/hpf/tabulate',
    name: 'HpfStatistics',
    component: () => import('@/views/hpf/HpfStatistics.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
});

export default router;
