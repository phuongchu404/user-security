package com.phuongcm.jpa.user.utils;


import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ServiceResult<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(String resultCode, String message){
        this.success = false;
        this.code = resultCode;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.success = true;
        this.data = data;
    }

    public static <T> ServiceResult<T> ok(T data){
        ServiceResult<T> result = new ServiceResult<>();
        result.setData(data);
        return result;
    }

    public static <T> ServiceResult<T> ok(){
        return ServiceResult.ok(null);
    }

    public static <T> ServiceResult<T> fail(String resultCode, String error){
        ServiceResult<T> resp = new ServiceResult<>();
        resp.setMessage(resultCode, error);
        return resp;
    }

    public static <T> ServiceResult<T> fail(ResultCode resultCode){
        return fail(resultCode.getCode(), resultCode.getDescription());
    }
    public static  <T> ServiceResult<T> fail(ServiceException e){
        return fail(e.getResultCode(), e.getMessage());
    }
    public T successData(){
        if(!isSuccess()){
            throw new ServiceException(getCode(), getMessage());
        }
        return data;
    }

    public void ifSuccess(Consumer<? super T> consumer) {
        consumer.accept(data);
    }

    public <X extends Throwable> T orElseThrow(BiFunction<String, String, ? extends X> function) throws X {
        if (isSuccess()) {
            return data;
        } else {
            throw function.apply(code, message);
        }
    }

    public T orElseGet(Supplier<? extends T> other) {
        return isSuccess() ? data : other.get();
    }

    public T orElseGet(BiFunction<String, String, T> function) {
        return isSuccess() ? data : function.apply(code, message);
    }

    public ServiceResult<T> orElseProcess(BiConsumer<String, String> consumer) {
        if (!isSuccess() && consumer != null) {
            consumer.accept(code, message);
        }
        return this;
    }
}
