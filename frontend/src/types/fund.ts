// Fund Module Types

// Fund Trade VO (Value Object for API)
export interface FundTradeVO {
  id: number;
  belong: string;
  fundCode: string;
  fundName: string;
  tradeType: string;
  tradeAmount: number;
  tradePrice: number;
  tradeNum: number;
  tradeDate: string;
  tradeTime: string;
  fee: number;
  totalAmount: number;
  createTime: string;
  updateTime: string;
}

// Fund Trade Info VO (for display)
export interface FundTradeInfoVO {
  fundCode: string;
  fundName: string;
  belong: string;
  tradeNum: number;
  costPrice: number;
  currentValue: number;
  currentPrice: number;
  profit: number;
  profitRate: number;
  tradeList: FundTradeVO[];
}

// Fund Index VO
export interface FundIndexVO {
  belong: string;
  totalAmount: number;
  totalProfit: number;
  totalProfitRate: number;
  fundList: FundTradeInfoVO[];
}

// Fund Statistics VO
export interface FundStatisticsVO {
  belong: string;
  totalInvest: number;
  totalCurrent: number;
  totalProfit: number;
  totalProfitRate: number;
  fundCount: number;
  profitFundCount: number;
  lossFundCount: number;
  fundDetails: FundStatisticsDetailVO[];
}

export interface FundStatisticsDetailVO {
  fundCode: string;
  fundName: string;
  investAmount: number;
  currentAmount: number;
  profit: number;
  profitRate: number;
  proportion: number;
}

// Form DTO for creating/updating fund trades
export interface FundTradeFormDTO {
  id?: number;
  belong: string;
  fundCode: string;
  fundName: string;
  tradeType: string;
  tradeAmount: number;
  tradePrice: number;
  tradeNum: number;
  tradeDate: string;
  tradeTime: string;
  fee: number;
}

// Query Parameters
export interface FundQueryParams {
  belong?: string;
  fundCode?: string;
  fundName?: string;
  startDate?: string;
  endDate?: string;
  page?: number;
  size?: number;
}
