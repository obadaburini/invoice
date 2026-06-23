package com.example.invoice;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.invoice.databinding.ActivityCreateInvoiceBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateInvoiceActivity extends AppCompatActivity {

    private ActivityCreateInvoiceBinding binding;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();

        binding.etInvoiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateInvoiceActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String formattedDate = dateFormat.format(calendar.getTime());
                            binding.etInvoiceDate.setText(formattedDate);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        binding.tilInvoiceDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etInvoiceDate.performClick();
            }
        });

        setupTotalAmountCalculation();

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Toast.makeText(CreateInvoiceActivity.this, "تم حفظ الفاتورة بنجاح!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void setupTotalAmountCalculation() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        binding.etQuantity.addTextChangedListener(textWatcher);
        binding.etUnitPrice.addTextChangedListener(textWatcher);
    }

    private void calculateTotalAmount() {
        try {
            String quantityStr = binding.etQuantity.getText().toString().trim();
            String unitPriceStr = binding.etUnitPrice.getText().toString().trim();

            if (quantityStr.isEmpty() || unitPriceStr.isEmpty()) {
                binding.etTotalAmount.setText("");
                return;
            }

            double quantity = Double.parseDouble(quantityStr);
            double unitPrice = Double.parseDouble(unitPriceStr);

            double total = quantity * unitPrice;

            binding.etTotalAmount.setText(String.format(Locale.getDefault(), "%.2f", total));
        } catch (NumberFormatException e) {
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (binding.etInvoiceNumber.getText().toString().trim().isEmpty()) {
            binding.tilInvoiceNumber.setError("يرجى إدخال رقم الفاتورة");
            isValid = false;
        } else {
            binding.tilInvoiceNumber.setError(null);
        }

        if (binding.etCustomerName.getText().toString().trim().isEmpty()) {
            binding.tilCustomerName.setError("يرجى إدخال اسم العميل");
            isValid = false;
        } else {
            binding.tilCustomerName.setError(null);
        }

        if (binding.etInvoiceDate.getText().toString().trim().isEmpty()) {
            binding.tilInvoiceDate.setError("يرجى اختيار تاريخ الفاتورة");
            isValid = false;
        } else {
            binding.tilInvoiceDate.setError(null);
        }

        if (binding.etItemDescription.getText().toString().trim().isEmpty()) {
            binding.tilItemDescription.setError("يرجى إدخال وصف الصنف");
            isValid = false;
        } else {
            binding.tilItemDescription.setError(null);
        }

        return isValid;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
