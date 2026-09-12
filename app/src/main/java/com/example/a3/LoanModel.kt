package com.example.a3

data class LoanModel(
    val price: String = "",
    val interestRate: Float = 1.0f,  // ดอกเบี้ยต่อเดือน (%)
    val months: Float = 12.0f,        // จำนวนเดือนผ่อน
    val monthlyPayment: Double = 0.0,  // ค่างวดต่อเดือน
    val totalPayment: Double = 0.0,    // ยอดรวมที่ต้องจ่ายจริง
    val totalInterest: Double = 0.0    // ดอกเบี้ยทั้งหมด
)