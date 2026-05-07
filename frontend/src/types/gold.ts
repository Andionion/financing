// Gold Module Types

// Gold Trade VO - 匹配后端实际结构
export interface GoldTradeVO {
  id: string;
  tradeDate: string;
  amount: number;
  unitPrice: number;
  weight: number;
  tradeType: string;
  goldType: string;
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
