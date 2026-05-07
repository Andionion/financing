import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';

// 预加载常用页面，提高访问速度
const Home = () => import('@/views/Home.vue');
const FundIndex = () => import('@/views/fund/FundIndex.vue');
const FundStatistics = () => import('@/views/fund/FundStatistics.vue');
const FundDetail = () => import('@/views/fund/FundDetail.vue');
const GoldTrade = () => import('@/views/gold/GoldTrade.vue');
const GoldStatistics = () => import('@/views/gold/GoldStatistics.vue');
const HpfRecords = () => import('@/views/hpf/HpfRecords.vue');
const HpfStatistics = () => import('@/views/hpf/HpfStatistics.vue');

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  // Fund Routes
  {
    path: '/fund',
    name: 'FundIndex',
    component: FundIndex,
  },
  {
    path: '/fund/tabulate/:belong',
    name: 'FundStatistics',
    component: FundStatistics,
    props: true,
  },
  {
    path: '/fund/:belong/:fundCode',
    name: 'FundDetail',
    component: FundDetail,
    props: true,
  },
  // Gold Routes
  {
    path: '/gold',
    name: 'Gold',
    component: GoldTrade,
  },
  {
    path: '/gold/tabulate',
    name: 'GoldStatistics',
    component: GoldStatistics,
  },
  // HPF Routes
  {
    path: '/hpf',
    name: 'Hpf',
    component: HpfRecords,
  },
  {
    path: '/hpf/tabulate',
    name: 'HpfStatistics',
    component: HpfStatistics,
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
