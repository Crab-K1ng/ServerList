package io.github.CrabK1ng.ServerList.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import finalforeach.cosmicreach.gamestates.ConnectingScreen;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.NetworkMenu;
import finalforeach.cosmicreach.ui.HorizontalAnchor;
import finalforeach.cosmicreach.ui.UIElement;
import finalforeach.cosmicreach.ui.VerticalAnchor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Iterator;

import static io.github.CrabK1ng.ServerList.ServerList.serverList;

@Mixin(NetworkMenu.class)
public class NetworkMenuMixin extends GameState implements InputProcessor {

    @Unique
    Array<UIElement> serverButtons = new Array();
    @Unique
    int topServerIdx;
    @Unique
    UIElement upButton;
    @Unique
    UIElement downButton;

    @Unique
    private void cycleServerButtons() {
        if (this.upButton != null) {
            if (this.topServerIdx > 0) {
                this.upButton.show();
            } else {
                this.upButton.hide();
            }
        }

        if (this.downButton != null) {
            this.downButton.hide();
        }

        float y = 100.0F;

        for(int i = 0; i < this.serverButtons.size; ++i) {
            UIElement serverButton = (UIElement)this.serverButtons.get(i);
            if (i < this.topServerIdx) {
                serverButton.hide();
            } else {
                serverButton.show();
                serverButton.y = y ;
                y += 75.0F;
                if (y > 550.0F) {
                    serverButton.hide();
                    if (this.downButton != null) {
                        this.downButton.show();
                    }
                }
            }
        }

    }

    @Unique
    public void create() {
        super.create();

        Gdx.input.setInputProcessor(this);

        float x = 0.0F;
        float y = 100.0F;
        Iterator<String> iterator = serverList.iterator();

        while (iterator.hasNext()){

            String address = iterator.next();

            UIElement enterServerButton = new UIElement(x, y, 300.0F, 50.0F) {
                @Override
                public void onClick() {
                    GameState.switchToGameState(new ConnectingScreen(address));
                }
            };
            this.serverButtons.add(enterServerButton);
            enterServerButton.vAnchor = VerticalAnchor.TOP_ALIGNED;
            enterServerButton.hAnchor = HorizontalAnchor.CENTERED;
            enterServerButton.setText(address);

            enterServerButton.show();
            this.uiObjects.add(enterServerButton);
            y += 75.0F;
        }

        this.upButton = new UIElement(x + 90.0F + 125.0F, -50.0F, 50.0F, 50.0F) {
            @Override
            public void onClick() {
                super.onClick();
                 NetworkMenuMixin.this.topServerIdx = MathUtils.clamp( NetworkMenuMixin.this.topServerIdx - 1, 0,  NetworkMenuMixin.this.serverButtons.size - 1);
                 NetworkMenuMixin.this.cycleServerButtons();
            }
        };
        this.upButton.vAnchor = VerticalAnchor.CENTERED;
        this.upButton.hAnchor = HorizontalAnchor.CENTERED;
        this.upButton.setText("^");
        this.upButton.show();
        this.uiObjects.add(this.upButton);

        this.downButton = new UIElement(x + 90.0F + 125.0F, 50.0F, 50.0F, 50.0F) {
            @Override
            public void onClick() {
                super.onClick();
                 NetworkMenuMixin.this.topServerIdx = MathUtils.clamp( NetworkMenuMixin.this.topServerIdx + 1, 0,  NetworkMenuMixin.this.serverButtons.size - 1);
                 NetworkMenuMixin.this.cycleServerButtons();
            }
        };
        this.downButton.vAnchor = VerticalAnchor.CENTERED;
        this.downButton.hAnchor = HorizontalAnchor.CENTERED;
        this.downButton.setText("V");
        this.downButton.show();
        this.uiObjects.add(this.downButton);
        this.cycleServerButtons();
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) {
            NetworkMenuMixin.this.topServerIdx = MathUtils.clamp( NetworkMenuMixin.this.topServerIdx + 1, 0,  NetworkMenuMixin.this.serverButtons.size - 1);
            NetworkMenuMixin.this.cycleServerButtons();
            return true;
        } else if (amountY < 0) {
            NetworkMenuMixin.this.topServerIdx = MathUtils.clamp( NetworkMenuMixin.this.topServerIdx - 1, 0,  NetworkMenuMixin.this.serverButtons.size - 1);
            NetworkMenuMixin.this.cycleServerButtons();
            return true;
        }
        return false;
    }

    @Override public boolean keyDown(int i) {return false;}
    @Override public boolean keyUp(int i) {return false;}
    @Override public boolean keyTyped(char c) {return false;}
    @Override public boolean touchDown(int i, int i1, int i2, int i3) {return false;}
    @Override public boolean touchUp(int i, int i1, int i2, int i3) {return false;}
    @Override public boolean touchCancelled(int i, int i1, int i2, int i3) {return false;}
    @Override public boolean touchDragged(int i, int i1, int i2) {return false;}
    @Override public boolean mouseMoved(int i, int i1) {return false;}

}
