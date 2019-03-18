package com.xxTFxx.siberianadv.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class MessageXYZ<REQ extends IMessage> extends MessageBase<REQ>{

    public MessageXYZ(){}


    public MessageXYZ(TileEntity te){
        this();
    }

    @Override
    public void fromBytes(ByteBuf buf){

    }

    @Override
    public void toBytes(ByteBuf buf){

    }


}