// Housing Provident Fund (HPF) Module Types

// HPF Record VO
export interface HpfRecordVO {
  id: number;
  belong: string;
  depositDate: string;
  depositAmount: number;
  companyAmount: number;
  totalAmount: number;
  balance: number;
  month: string;
  createTime: string;
  updateTime: string;
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

// Form DTO
export interface HpfRecordFormDTO {
  id?: number;
  belong: string;
  depositDate: string;
  depositAmount: number;
  companyAmount: number;
  totalAmount: number;
  balance: number;
  month: string;
}

// Query Parameters
export interface HpfQueryParams {
  belong?: string;
  startDate?: string;
  endDate?: string;
  month?: string;
  page?: number;
  size?: number;
}
