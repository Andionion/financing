// Housing Provident Fund (HPF) Module Types

// HPF Record VO - 匹配后端实际结构
export interface HpfRecordVO {
  id: string;
  operationDate: string;
  operationType: string;
  amount: number;
  balance: number;
}

// HPF Statistics VO
export interface HpfStatisticsVO {
  totalDeposit: number;
  totalCompany: number;
  totalAmount: number;
  currentBalance: number;
  recordCount: number;
  monthCount: number;
  monthlyAverage: number;
  monthlyDetails: HpfMonthlyDetailVO[];
}

export interface HpfMonthlyDetailVO {
  month: string;
  personalAmount: number;
  companyAmount: number;
  totalAmount: number;
  balance: number;
}

// Form DTO - 匹配后端实际结构
export interface HpfRecordFormDTO {
  id?: string;
  operationDate: string;
  operationType: string;
  amount: number;
  balance?: number;
}

// Query Parameters
export interface HpfQueryParams {
  page?: number;
  size?: number;
}
