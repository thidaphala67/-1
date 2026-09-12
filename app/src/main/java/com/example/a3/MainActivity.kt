package com.example.a3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LoanCalculatorScreen()
            }
        }
    }
}

@Composable
fun LoanCalculatorScreen(
    loanViewModel: LoanViewModel = viewModel()
) {
    val uiState by loanViewModel.uiState.collectAsState()
    val formatter = DecimalFormat("#,##0.00")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ระบบคำนวณค่างวดผ่อนชำระ (MVVM)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = uiState.price,
            onValueChange = { loanViewModel.onPriceChange(it) },
            label = { Text("ราคาสินค้า (บาท)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "อัตราดอกเบี้ยต่อเดือน: ${String.format("%.1f", uiState.interestRate)}%",
            modifier = Modifier.align(Alignment.Start)
        )
        Slider(
            value = uiState.interestRate,
            onValueChange = { loanViewModel.onInterestRateChange(it) },
            valueRange = 0f..5f,
            steps = 49,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "จำนวนเดือนในการผ่อน: ${uiState.months.toInt()} เดือน",
            modifier = Modifier.align(Alignment.Start)
        )
        Slider(
            value = uiState.months,
            onValueChange = { loanViewModel.onMonthsChange(it) },
            valueRange = 3f..36f,
            steps = 10,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loanViewModel.calculateLoan() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("คำนวณค่างวด", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ผ่อนชำระต่อเดือน: ${formatter.format(uiState.monthlyPayment)} บาท",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
                Text(text = "ยอดดอกเบี้ยทั้งหมด: ${formatter.format(uiState.totalInterest)} บาท")
                Text(text = "ยอดรวมที่ต้องจ่ายจริง: ${formatter.format(uiState.totalPayment)} บาท")
            }
        }
    }
}