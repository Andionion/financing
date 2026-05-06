// Gold Module Types

// Gold Trade VO
export interface GoldTradeVO {
  id: number;
  tradeType: string;
  tradeAmount: number;
  tradePrice: number;
  tradeNum: number;
  tradeDate: string;
  tradeTime: string;
  fee: number;
  totalAmount: number;
  remark: string;
  createTime: string;
  updateTime: string;
}

// Gold Statistics VO
export interface GoldStatisticsVO {
  totalInvest: number;
  totalCurrent: number;
  totalProfit: number;
  totalProfitRate: number;
  tradeCount: number;
  buyCount: number;
  sellCount: number;
  currentGoldNum: number;
  currentGoldPrice: number;
}

// Form DTO
export interface GoldTradeFormDTO {
  id?: number;
  tradeType: string;
  tradeAmount?: number;
  tradePrice: number;
  tradeNum: number;
  tradeDate: string;
  tradeTime: string;
  fee: number;
  remark?: string;
}

// Query Parameters
export interface GoldQueryParams {
  startDate?: string;
  endDate?: string;
  tradeType?: string;
  page?: number;
  size?: number;
}
