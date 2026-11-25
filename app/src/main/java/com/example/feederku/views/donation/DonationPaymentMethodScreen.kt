package com.example.feederku.views.donation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feederku.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationPaymentMethodScreen(
    campaignId: String,
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit, // method name
    viewModel: DonationViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedMethod by remember { mutableStateOf("Credit Card") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Method", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = headerFooter,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = ColorFogCream
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(Modifier.height(24.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("ENTER DONATION AMOUNT (IDR)", fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                                Surface(
                                    color = buttonGreen,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        uiState.selectedCampaign?.title ?: "Donation Campaign",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = if (uiState.donationAmount == 0L) "" else uiState.donationAmount.toString(),
                                onValueChange = { viewModel.onAmountChange(it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("e.g. 50000") },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = buttonGreen,
                                    unfocusedBorderColor = Color.LightGray
                                )
                            )

                            val fee = 2000L
                            val total = uiState.donationAmount + fee
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Payment (incl. Rp 2,000 fee):", fontSize = 13.sp, color = textBrown, fontWeight = FontWeight.Medium)
                                Text("Rp %,d".format(total), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = greenDark)
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }

                item { Text("E-Wallet", fontWeight = FontWeight.Bold, color = textBrown) }
                
                item { PaymentMethodItem("GoPay", Icons.Default.AccountBalanceWallet, selectedMethod == "GoPay") { selectedMethod = "GoPay" } }
                item { PaymentMethodItem("OVO", Icons.Default.AccountBalanceWallet, selectedMethod == "OVO") { selectedMethod = "OVO" } }
                item { PaymentMethodItem("Dana", Icons.Default.AccountBalanceWallet, selectedMethod == "Dana") { selectedMethod = "Dana" } }

                item { Text("Bank Transfer", fontWeight = FontWeight.Bold, color = textBrown) }
                
                item { PaymentMethodItem("BCA Virtual Account", Icons.Default.AccountBalance, selectedMethod == "BCA") { selectedMethod = "BCA" } }
                item { PaymentMethodItem("Mandiri Virtual Account", Icons.Default.AccountBalance, selectedMethod == "Mandiri") { selectedMethod = "Mandiri" } }
                item { PaymentMethodItem("BRI Virtual Account", Icons.Default.AccountBalance, selectedMethod == "BRI") { selectedMethod = "BRI" } }
                
                item { PaymentMethodItem("Credit/Debit Card", Icons.Default.CreditCard, selectedMethod == "Credit Card") { selectedMethod = "Credit Card" } }
                
                item { Spacer(Modifier.height(24.dp)) }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ColorFogCream,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { 
                        viewModel.setSelectedPaymentMethod(selectedMethod)
                        onContinueClick(campaignId) 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonGreen),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    name: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = ColorLinenWarm,
        border = if (isSelected) BorderStroke(2.dp, buttonGreen) else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = textBrown, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(16.dp))
            Text(name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = textBrown)
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = textBrown)
            )
        }
    }
}
