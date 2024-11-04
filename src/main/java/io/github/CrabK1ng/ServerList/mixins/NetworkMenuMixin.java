package io.github.CrabK1ng.ServerList.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import finalforeach.cosmicreach.gamestates.ConnectingScreen;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.KeybindsMenu;
import finalforeach.cosmicreach.gamestates.NetworkMenu;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.settings.Keybind;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.UITextInput;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import io.github.CrabK1ng.ServerList.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

import static io.github.CrabK1ng.ServerList.ServerList.serverList;

@Mixin(NetworkMenu.class)
public class NetworkMenuMixin extends GameState{

    @Shadow
    private static String addressLabel;

    int ix = 0;

    @Unique
    private void addAddressButton(String address) {
        ServerList.LOGGER.info(String.valueOf(this.ix));

        if (this.ix == 6){
            return;
        }

        UIElement multiplayerButton = new UIElement(0.0F, (float)(100 + 75 * this.ix) , 300.0F, 50.0F) {
            public void onClick() {
                super.onClick();
                GameState.switchToGameState(new ConnectingScreen(address));
            }
        };
        multiplayerButton.hAnchor = HorizontalAnchor.CENTERED;
        multiplayerButton.vAnchor = VerticalAnchor.TOP_ALIGNED;
        multiplayerButton.setText(Lang.get(address));
        multiplayerButton.show();
        this.uiObjects.add(multiplayerButton);
        ++this.ix;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void NetworkMenu(GameState previousState, CallbackInfo ci){
        ServerList.LOGGER.info("hi");

        Iterator<String> iterator = serverList.iterator();


        while (iterator.hasNext()){
            String Address = iterator.next();

            addAddressButton(Address);
        }

    }

}
