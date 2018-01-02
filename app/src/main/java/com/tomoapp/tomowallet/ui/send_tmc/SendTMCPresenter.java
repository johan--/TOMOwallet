package com.tomoapp.tomowallet.ui.send_tmc;

import com.tomoapp.tomowallet.R;
import com.tomoapp.tomowallet.base.MainApplication;
import com.tomoapp.tomowallet.helper.api.APIService;
import com.tomoapp.tomowallet.model.wallet.WalletDataSource;
import com.tomoapp.tomowallet.model.wallet.WalletRepository;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 12/27/17.
 */

public class SendTMCPresenter implements SendTMCContract.Presenter {

    private SendTMCContract.View mView;
    private WalletDataSource mWallet;


    public SendTMCPresenter(SendTMCContract.View mView) {
        this.mView = mView;
        this.mWallet = new WalletRepository(mView.getContext());
    }


    @Override
    public void init() {
        mView.loadContent();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onTransfer(String address, String amount) {
        if (address == null || address.isEmpty()){
            mView.displayError(mView.getContext().getString(R.string.invalid_address));
            return;
        }
        try {
            Double transferValue = Double.valueOf(amount);
            if (transferValue <= 0 || transferValue > getTMCInSideChain()){
                mView.displayError(mView.getContext().getString(R.string.invalid_value));
                return;
            }
            mView.confirmTransfer(address, transferValue);
        } catch (NumberFormatException e) {
            mView.displayError(mView.getContext().getString(R.string.invalid_value));
            return;
        }


    }


    @Override
    public void performTransfer(String address, Double amount) {
        mView.onTransferring();
        APIService.getInstance().build()
                .transfer(mWallet.getAddress(), address, amount)
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    @Override
    public Double getTMCInSideChain() {
        return MainApplication.get().getTmcInSideChain();
    }
}
