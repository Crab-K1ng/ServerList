package io.github.CrabK1ng.ServerList.mixins;

import finalforeach.cosmicreach.gamestates.ConnectingScreen;
import io.github.CrabK1ng.ServerList.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.CrabK1ng.ServerList.ServerList.serverList;

@Mixin(ConnectingScreen.class)
public class ConnectingScreenMixin {


    @Inject(method = "<init>", at = @At("TAIL"))
    public void ConnectingScreen(String address, CallbackInfo ci){
        if (!serverList.contains(address)){
            serverList.add(address);
            ServerList.saveServerIp();
        }
    }
}
